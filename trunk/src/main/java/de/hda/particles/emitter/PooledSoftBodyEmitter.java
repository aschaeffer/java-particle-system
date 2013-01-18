package de.hda.particles.emitter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.newdawn.slick.util.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.domain.*;
import de.hda.particles.features.MassSpring;
import de.hda.particles.features.ParticleFeature;
import de.hda.particles.modifier.velocity.FixedPointMassSpringTransformation;
import de.hda.particles.modifier.velocity.MassSpringTransformation;

/**
 * soft body object emitter
 * 
 * ein 3d objekt wird geladen
 * aus den vertices werden fixed points erzeugt
 * zu jedem fixed point wird ein partikel erzeugt
 * verbundene vertices des objekts -> verbinden der partikel mit federn
 * problem: die springs haben eine feste länge!
 * 
 * @author aschaeffer
 *
 */
public class PooledSoftBodyEmitter extends AbstractParticleEmitter implements ParticleEmitter {

	// TODO: make filename editable
	// TODO: add toggle enable/disable fixed points
	// TODO: 
	
	private final static String filename = "objects/head/luisobj.obj";
	private final static Float SCALE = 6.0f;

	// obj keywords
    private final static String OBJ_VERTEX = "v";
    private final static String OBJ_VERTEX_NORMAL = "vn";
    private final static String OBJ_VERTEX_TEXTURE = "vt";
    private final static String OBJ_FACE = "f";
    private final static String OBJ_GROUP_NAME = "g";
    private final static String OBJ_OBJECT_NAME = "o";
	
	private Double springLength;
	private Double springFriction;
	private Double springConstant;
	
	private int particleIndex1;
	private int particleIndex2;
	private Particle particle1;

    private BufferedReader bufferedReader = null;
    
	public PooledSoftBodyEmitter() {}

	private final Logger logger = LoggerFactory.getLogger(PooledSoftBodyEmitter.class);

	@Override
	public void update() {
		pastIterations++;
		if (rate > 0) createSoftBodyFromFile();
	}

	public void prepare() {
		springLength = (Double) configuration.get(MassSpring.SPRING_LENGTH);
		if (springLength == null) {
			springLength = MassSpring.DEFAULT_SPRING_LENGTH;
			configuration.put(MassSpring.SPRING_LENGTH, MassSpring.DEFAULT_SPRING_LENGTH);
		}
		springFriction = (Double) configuration.get(MassSpring.SPRING_FRICTION);
		if (springFriction == null) {
			springFriction = MassSpring.DEFAULT_SPRING_FRICTION;
			configuration.put(MassSpring.SPRING_FRICTION, MassSpring.DEFAULT_SPRING_FRICTION);
		}
		springConstant = (Double) configuration.get(MassSpring.SPRING_CONSTANT);
		if (springConstant == null) {
			springConstant = MassSpring.DEFAULT_SPRING_CONSTANT;
			configuration.put(MassSpring.SPRING_CONSTANT, MassSpring.DEFAULT_SPRING_CONSTANT);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void createSoftBodyFromFile() {
		logger.info("create soft body from obj file: " + filename);
		prepare();
		try {
			List<Particle> newParticles = new ArrayList<Particle>();
			List<FixedPoint> newFixedPoints = new ArrayList<FixedPoint>();

			
			InputStream inputStream = ResourceLoader.getResourceAsStream(filename); // this.getClass().getResourceAsStream(filename);
			if (inputStream == null) {
				logger.error("Could not find " + filename);
				return;
			}
	        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	
	        String line = null;
	        while (true) {
	            line = bufferedReader.readLine();
	            if (null == line) break;
	            line = line.trim();
	            if (line.length() == 0) continue;
	
	            // NOTE: we don't check for the space after the char
	            // because sometimes it's not there - most notably in the
	            // grouupname, we seem to get a lot of times where we have
	            // "g\n", i.e. setting the group name to blank (or
	            // default?)
	            if (line.startsWith("#")) { // comment
	                continue;
	            } else if (line.startsWith(OBJ_VERTEX_TEXTURE)) {
	            } else if (line.startsWith(OBJ_VERTEX_NORMAL)) {
	            } else if (line.startsWith(OBJ_VERTEX)) {
	            	// create fixed point and particle
	                float[] vertexValues = parseFloatList(3, line, OBJ_VERTEX.length());
	                Particle particle = generateParticle(vertexValues[0], vertexValues[1], vertexValues[2]);
	            	newParticles.add(particle);
	        		particleSystem.addParticle(particle);
	        		FixedPoint fixedPoint = generateFixedPoint(vertexValues[0], vertexValues[1], vertexValues[2]);
	            	newFixedPoints.add(fixedPoint);
	        		particleSystem.addFixedPoint(fixedPoint);
	        		// connect particle to fixed point
	        		fixedPoint.addSpring(new Pair<Particle, Float>(particle, 0.0f)); // we want the particle stay near the fixed point
	            } else if (line.startsWith(OBJ_FACE)) {
	            	// connect particles of each face using springs
	        		line = line.substring(OBJ_FACE.length()).trim();
	                int[] verticeIndexAry = parseListVerticeNTuples(line, 2);
	                if (verticeIndexAry.length > 0) {
	                	List<Particle> faceParticles = new ArrayList<Particle>();
		                for (int i = 1; i < (verticeIndexAry.length/2); i++) {
		                	particleIndex1 = verticeIndexAry[i*2 - 2] - 1;
		                	particleIndex2 = verticeIndexAry[i*2] - 1;
		                	particle1 = newParticles.get(particleIndex1);
		                	faceParticles.add(particle1);
							ArrayList<Particle> springConnectedParticles = (ArrayList<Particle>) particle1.get(MassSpring.SPRING_CONNECTED_PARTICLES);
		                	if (springConnectedParticles == null) springConnectedParticles = new ArrayList<Particle>();
		                	springConnectedParticles.add(newParticles.get(particleIndex2));
			    			particle1.put(MassSpring.SPRING_CONNECTED_PARTICLES, springConnectedParticles);
		                }
	                	particleIndex1 = verticeIndexAry[verticeIndexAry.length - 2] - 1;
	                	particleIndex2 = verticeIndexAry[0] - 1;
		                particle1 = newParticles.get(particleIndex1);
		                faceParticles.add(particle1);
						ArrayList<Particle> springConnectedParticles = (ArrayList<Particle>) particle1.get(MassSpring.SPRING_CONNECTED_PARTICLES);
	                	if (springConnectedParticles == null) springConnectedParticles = new ArrayList<Particle>();
	                	springConnectedParticles.add(newParticles.get(particleIndex2));
		    			particle1.put(MassSpring.SPRING_CONNECTED_PARTICLES, springConnectedParticles);
		                particleSystem.addFace(generateFace(faceParticles));
	                }
	            } else if (line.startsWith(OBJ_GROUP_NAME)) {
	            } else if (line.startsWith(OBJ_OBJECT_NAME)) {
	            }
	        }
	        bufferedReader.close();
		} catch (IOException e) {
			logger.error("File not found / IO Error: " + e.getMessage(), e);
		}
		rate = 0;
	}

	private Particle generateParticle(float x, float y, float z) {
		Particle particle = particleSystem.getParticlePool().next();
		particle.setPastIterations(0);
		particle.setMass(Particle.DEFAULT_MASS);
		particle.setVisibility(true);
		particle.setIndex(0);
		particle.clear();
		particle.setX(position.x + x * SCALE);
		particle.setY(position.y + y * SCALE);
		particle.setZ(position.z + z * SCALE);
		particle.setVelocity(particleDefaultVelocity);
		particle.setParticleRendererIndex(particleRendererIndex);
		particle.setRemainingIterations(particleLifetime);
		for (ParticleFeature particleFeature: particleSystem.getParticleFeatures()) {
			particleFeature.init(this, particle);
		}
		particle.put(MassSpring.SPRING_LENGTH, springLength);
		particle.put(MassSpring.SPRING_FRICTION, springFriction);
		particle.put(MassSpring.SPRING_CONSTANT, springConstant);
		particle.put(MassSpring.SPRING_CONNECTED_PARTICLES, new ArrayList<Particle>());
		return particle;
	}
	
	private FixedPoint generateFixedPoint(float x, float y, float z) {
		FixedPoint fixedPoint = new DefaultMassSpringFixedPoint();
		fixedPoint.setMass(FixedPoint.DEFAULT_MASS);
		fixedPoint.setX(position.x + x * SCALE);
		fixedPoint.setY(position.y + y * SCALE);
		fixedPoint.setZ(position.z + z * SCALE);
		fixedPoint.setSpringFriction(springFriction.floatValue());
		fixedPoint.setSpringConstant(springConstant.floatValue());
		return fixedPoint;
	}

	private Face generateFace(List<Particle> particles) {
		Face face = particleSystem.getFacePool().next();
		face.setFaceRendererIndex(faceRendererIndex);
		face.clear();
		face.addAll(particles);
		return face;
	}

    private int skipWhiteSpace(int mCount, char messageChars[], String errMsg) {
        //Skip whitespace
        while (mCount < messageChars.length) {
            if (messageChars[mCount] == ' ' || messageChars[mCount] == '\n' || messageChars[mCount] == '\t') {
                mCount++;
            } else {
                break;
            }
        }
        if (errMsg != null) {
            if (mCount >= messageChars.length) {
                // printErrMsg("RString.skipWhiteSpace", errMsg, mCount, messageChars);
                return -1;
            }
        }
        return mCount;
    }

    private float[] parseFloatList(int numFloats, String list, int startIndex) {
        if (list == null) return null;
        if (list.equals("")) return null;
        float[] returnArray = new float[numFloats];
        int returnArrayCount = 0;

        // Copy list into a char array.
        char listChars[];
        listChars = new char[list.length()];
        list.getChars(0, list.length(), listChars, 0);
        int listLength = listChars.length;

        int count = startIndex;
        int itemStart = startIndex;
        int itemEnd = 0;
        int itemLength = 0;

        while (count < listLength) {
            // Skip any leading whitespace
            itemEnd = skipWhiteSpace(count, listChars, null);
            count = itemEnd;
            if (count >= listLength) {
                break;
            }
            itemStart = count;
            itemEnd = itemStart;
            while (itemEnd < listLength) {
                if ((listChars[itemEnd] != ' ') && (listChars[itemEnd] != '\n') && (listChars[itemEnd] != '\t')) {
                    itemEnd++;
                } else {
                    break;
                }
            }
            itemLength = itemEnd - itemStart;
            // logger.debug(new String(listChars, itemStart, itemLength));
            returnArray[returnArrayCount++] = Float.parseFloat(new String(listChars, itemStart, itemLength));
            if (returnArrayCount >= numFloats) {
                break;
            }

            count = itemEnd;
        }
        return returnArray;
    }

    private int[] parseListVerticeNTuples(String list, int expectedValuesPerTuple) {
        if (list == null) {
            return null;
        }
        if (list.equals("")) {
            return null;
        }

        String[] vertexStrings = parseWhitespaceList(list);

        ArrayList<Integer> returnList = new ArrayList<Integer>();
        Integer emptyMarker = Integer.MIN_VALUE;

        for (int loopi = 0; loopi < vertexStrings.length; loopi++) {
            parseVerticeNTuple(vertexStrings[loopi], returnList, emptyMarker, expectedValuesPerTuple);
        }

        int returnArray[] = new int[returnList.size()];
        for (int loopi = 0; loopi < returnList.size(); loopi++) {
            returnArray[loopi] = returnList.get(loopi);
        }
        return returnArray;
    }
    
    private void parseVerticeNTuple(String list, ArrayList<Integer> returnList, Integer emptyMarker, int expectedValueCount) {
        String[] numbers = parseList('/', list);
        int foundCount = 0;
        int index = 0;
        while (index < numbers.length) {
            if (numbers[index].trim().equals("")) {
                returnList.add(emptyMarker);
            } else {
                returnList.add(Integer.parseInt(numbers[index]));
            }
            foundCount++;
            index++;
        }
        while (foundCount < expectedValueCount) {
            returnList.add(emptyMarker);
            foundCount++;
        }
    }
    
    private String[] parseList(char delim, String list) {
        if (list == null) {
            return null;
        }
        if (list.equals("")) {
            return null;
        }

        ArrayList<String> returnVec = new ArrayList<String>();
        String[] returnArray = null;

        // Copy list into a char array.
        char listChars[];
        listChars = new char[list.length()];
        list.getChars(0, list.length(), listChars, 0);

        int count = 0;
        int itemStart = 0;
        int itemEnd = 0;
        String newItem = null;

        while (count < listChars.length) {
            count = itemEnd;
            if (count >= listChars.length) {
                break;
            }
            itemStart = count;
            itemEnd = itemStart;
            while (itemEnd < listChars.length) {
                if (delim != listChars[itemEnd]) {
                    itemEnd++;
                } else {
                    break;
                }
            }
            newItem = new String(listChars, itemStart, itemEnd - itemStart);
            itemEnd++;
            count = itemEnd;
            returnVec.add(newItem);
        }
        // Convert from vector to array, and return it.
        returnArray = new String[1];
        returnArray = (String[]) returnVec.toArray((Object[]) returnArray);
        return returnArray;
    }
 
    private String[] parseWhitespaceList(String list) {
        if (list == null) {
            return null;
        }
        if (list.equals("")) {
            return null;
        }

        ArrayList<String> returnVec = new ArrayList<String>();
        String[] returnArray = null;

        // Copy list into a char array.
        char listChars[];
        listChars = new char[list.length()];
        list.getChars(0, list.length(), listChars, 0);

        int count = 0;
        int itemStart = 0;
        int itemEnd = 0;
        String newItem = null;

        while (count < listChars.length) {
            // Skip any leading whitespace
            itemEnd = skipWhiteSpace(count, listChars, null);
            count = itemEnd;
            if (count >= listChars.length) {
                break;
            }
            itemStart = count;
            itemEnd = itemStart;
            while (itemEnd < listChars.length) {
                if ((listChars[itemEnd] != ' ') && (listChars[itemEnd] != '\n') && (listChars[itemEnd] != '\t')) {
                    itemEnd++;
                } else {
                    break;
                }
            }
            newItem = new String(listChars, itemStart, itemEnd - itemStart);
            itemEnd++;
            count = itemEnd;
            returnVec.add(newItem);
        }
        // Convert from vector to array, and return it.
        returnArray = new String[1];
        returnArray = (String[]) returnVec.toArray((Object[]) returnArray);
        return returnArray;
    }

	@Override
	public void setup() {}

	@Override
	public void destroy() {}

	@Override
	public void addDependencies() {
		particleSystem.addParticleFeature(MassSpring.class);
		particleSystem.addParticleModifier(MassSpringTransformation.class);
		particleSystem.addParticleModifier(FixedPointMassSpringTransformation.class);
	}
    
}
