
// --------------------------------------------------------------------------------------------------
// PARAMETERS:
// --------------------------------------------------------------------------------------------------

float4x4 tWVP : WORLDVIEWPROJECTION;

float4 backColor : Color;

//the data structure: "vertexshader to pixelshader"
//used as output data with the VS function
//and as input data with the PS function
struct vs2ps
{
    float4 Pos : POSITION;
    float3 rD : TEXCOORD0;
};

// --------------------------------------------------------------------------------------------------
// VERTEXSHADERS
// --------------------------------------------------------------------------------------------------

vs2ps VS(
    float4 Pos : POSITION,
    float3 rDi : TEXCOORD0)
{
    //inititalize all fields of output struct with 0
    vs2ps Out;

    //transform position
    Out.Pos = mul(Pos, tWVP);

    //pass ray direction
    Out.rD = rDi;

    return Out;
}

struct GRIDCELL {

   float3 p[8];
   float val[8];
};

/*
   Linearly interpolate the position where an isosurface cuts
   an edge between two vertices, each with their own scalar value
*/

float isolevel;
float cubesize = 0.1;

// ISO FUNCTIONS ---------------------------------------------------------------

float3 a;
float3 b;

float t = 1;

//the blob, this function defines the look of the blobs.
//you can put in here any function of a 3d point that has
//some roots close to the origin.

float getVertexValue(float3 p){

   float3 sqrP = p*p;

   return sqrP.x + sqrP.y + sqrP.z + b.x*sin(a.x*p.x) + b.y*sin(a.y*p.y) + b.z*sin(a.z*p.z)-t;

}

// ISO FUNCTIONS END -----------------------------------------------------------


// --------------------------------------------------------------------------------------------------
// PIXELSHADERS:
// --------------------------------------------------------------------------------------------------
///////////////////////////////////////
//
//  ported from:
//
//  QJuliaFragment.cg
//  4/17/2004
//
//  october 2005 by tebjan halm
//
//  Intersects a ray with the qj set w/ parameter mu and returns
//  the color of the phong shaded surface (estimate)
//
//     Keenan Crane (kcrane@uiuc.edu)
//
//
//

// Some constants used in the ray tracing process.  (These constants
// were determined through trial and error and are not by any means
// optimal.)

float BOUNDING_RADIUS_2 = 4.0;  // square of radius of a bounding sphere for the set used to accelerate intersection


float epsilon;          // specifies precision of intersection
float3 eye;             // location of the viewer
float3 light = 2;           // location of a single point light



// ---------- intersectQJulia() ------------------------------------------
//
// Finds the intersection of a ray with origin rO and direction rD with the
// quaternion Julia set specified by quaternion constant c.  The intersection
// is found using iterative sphere tracing, which takes a conservative step
// along the ray at each iteration by estimating the minimum distance between
// the current ray origin and the closest point in the Julia set.  The
// parameter maxIterations is passed on to iterateIntersect() which determines
// whether the current ray origin is in (or near) the set.
//

float intersectObject( inout float3 rO, float3 rD)
{

   float dist ; // the (approximate) distance between the first point along the ray within
               // epsilon of some point in the Julia set, or the last point to be tested if
               // there was no intersection.

   do {

      dist = getVertexValue(rO);  //distance to surface

      rO += rD * epsilon * dist; // (step)

      // Intersection testing finishes if we're close enough to the surface
      // (i.e., we're inside the epsilon isosurface of the distance estimator
      // function) or have left the bounding sphere.
      //if( dist < epsilon || dot(rO, rO) > BOUNDING_RADIUS_2 )
      //   break;
   } while ( dist >= isolevel && abs(dot(rO, rO)) <= BOUNDING_RADIUS_2 );

   // return the distance for this ray
   return dist;
}

float3 normEstimate(float3 p, float3 rD){

  GRIDCELL cube;
  GRIDCELL cubeVector;
  float3 normalAverage = 0;
  float csh = cubesize * 0.5;

  cube.p[0] = p + float3(-csh, -csh,  csh);
  cube.p[1] = p + float3( csh, -csh,  csh);
  cube.p[2] = p + float3( csh, -csh, -csh);
  cube.p[3] = p + float3(-csh, -csh, -csh);
  cube.p[4] = p + float3(-csh,  csh,  csh);
  cube.p[5] = p + float3( csh,  csh,  csh);
  cube.p[6] = p + float3( csh,  csh, -csh);
  cube.p[7] = p + float3(-csh,  csh, -csh);
  
  cubeVector.p[0] = float3(-csh, -csh,  csh);
  cubeVector.p[1] = float3( csh, -csh,  csh);
  cubeVector.p[2] = float3( csh, -csh, -csh);
  cubeVector.p[3] = float3(-csh, -csh, -csh);
  cubeVector.p[4] = float3(-csh,  csh,  csh);
  cubeVector.p[5] = float3( csh,  csh,  csh);
  cubeVector.p[6] = float3( csh,  csh, -csh);
  cubeVector.p[7] = float3(-csh,  csh, -csh);

  for(int i = 0; i < 8; i++){
    cube.val[i] = abs(getVertexValue(cube.p[i]));
  }

  for(int i = 0; i < 8; i++){
    normalAverage += cubeVector.p[i]*cube.val[i];
  }

  return normalize(normalAverage);


}

float3 normEstimate2(float3 p){

  return normalize(cross(ddx(p), ddy(p)));


}

// ----------- Phong() --------------------------------------------------
//
// Computes the direct illumination for point pt with normal N due to
// a point light at light and a viewer at eye.
//

//light properties

float4 lAmb  : COLOR <String uiname="Ambient Color";>  = {0.15, 0.15, 0.15, 1};
float4 lDiff : COLOR <String uiname="Diffuse Color";>  = {0.85, 0.85, 0.85, 1};
float4 lSpec : COLOR <String uiname="Specular Color";> = {0.35, 0.35, 0.35, 1};
float lPower <String uiname="Power"; float uimin=0.0;> = 25.0;     //shininess of specular highlight


float3 Phong( float3 light, float3 eye, float3 pt, float3 N )
{
   float3 diffuse = lDiff.rgb;       // base color of shading

   //float3 L     = normalize( light - pt );  // find the vector to the light
   float3 E     = normalize( eye   - pt );  // find the vector to the eye
   //float  NdotL = dot( N, L );              // find the cosine of the angle between light and normal
   //float3 R     = L - 2 * NdotL * N;        // find the reflected vector

   //diffuse += abs( N )*0.1;  // add some of the normal to the
                             // color to make it more interesting
    lAmb.a = 1;
    //halfvector
    float3 H = normalize(E + light);

    //compute blinn lighting
    float3 shades = lit(dot(N, light), dot(N, H), lPower);

    float4 diff = lDiff * shades.y;
    diff.a = 1;

    //reflection vector (view space)
    float3 R = normalize(2 * dot(N, light) * N - light);
    //normalized view direction (view space)

    //calculate specular light
    float4 spec = pow(max(dot(R, E),0), lPower*.2) * lSpec;

    float4 col = 1;
    col.rgb *= (lAmb + diff) + spec;

   // compute the illumination using the Phong equation
   return col;
}

// ---------- intersectSphere() ---------------------------------------
//
// Finds the intersection of a ray with a sphere with statically
// defined radius BOUNDING_RADIUS centered around the origin.  This
// sphere serves as a bounding volume for the Julia set.

float3 intersectSphere( float3 rO, float3 rD )
{
   float B, C, d, t0, t1, t;

   B = 2 * dot( rO, rD );
   C = dot( rO, rO ) - BOUNDING_RADIUS_2;
   d = sqrt( B*B - 4*C );
   t0 = ( -B + d ) * 0.5;
   t1 = ( -B - d ) * 0.5;
   t = min( t0, t1 );
   rO += t * rD;

   return rO;
}

// ------------ main() -------------------------------------------------
//
//  Each fragment performs the intersection of a single ray with
//  the quaternion Julia set.  In the current implementation
//  the ray's origin and direction are passed in on texture
//  coordinates, but could also be looked up in a texture for a
//  more general set of rays.
//
//  The overall procedure for intersection performed in main() is:
//
//  -move the ray origin forward onto a bounding sphere surrounding the Julia set
//  -test the new ray for the nearest intersection with the Julia set
//  -if the ray does include a point in the set:
//      -estimate the gradient of the potential function to get a "normal"
//      -use the normal and other information to perform Phong shading
//      -cast a shadow ray from the point of intersection to the light
//      -if the shadow ray hits something, modify the Phong shaded color to represent shadow
//  -return the shaded color if there was a hit and the background color otherwise
//


float4 PS_Object( float3 rD : TEXCOORD0): COLOR
{

   float4 col;  // This color is the final output of our program.
   // Initially set the output color to the background color.  It will stay
   // this way unless we find an intersection with the Julia set.
   float3 rO = eye;
   col = backColor;


   // First, intersect the original ray with a sphere bounding the set, and
   // move the origin to the point of intersection.  This prevents an
   // unnecessarily large number of steps from being taken when looking for
   // intersection with the Julia set.

   rD = normalize( rD );  //the ray direction is interpolated and may need to be normalized
   float3 rDir = rD;
   rO = intersectSphere( rO, rD );

   // Next, try to find a point along the ray which intersects the Julia set.
   // (More details are given in the routine itself.)

   float dist = intersectObject( rO, rD);

   // We say that we found an intersection if our estimate of the distance to
   // the set is smaller than some small value epsilon.  In this case we want
   // to do some shading / coloring.

   if( dist < isolevel )
   {
      // Determine a "surface normal" which we'll use for lighting calculations.

      float3 N = normEstimate(rO, rD);

      //N = normalize(2 * N + ddx(N) + ddy(N));

      // Compute the Phong illumination at the point of intersection.
      col.rgb = Phong( light, rD, rO, N );
      col.a = 1;  // (make this fragment opaque)

   }

   // Return the final color which is still the background color if we didn't hit anything.
   return col;
}

// --------------------------------------------------------------------------------------------------
// TECHNIQUES:
// --------------------------------------------------------------------------------------------------


technique TObject
{
    pass P0
    {
        //Wrap0 = U;  // useful when mesh is round like a sphere
        VertexShader = compile vs_1_1 VS();
        PixelShader  = compile ps_3_0 PS_Object();
    }
}
