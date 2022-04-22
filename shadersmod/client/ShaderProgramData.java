package shadersmod.client;

import org.lwjgl.opengl.*;

public class ShaderProgramData
{
    public int programIDGL;
    public int uniform_texture;
    public int uniform_lightmap;
    public int uniform_normals;
    public int uniform_specular;
    public int uniform_shadow;
    public int uniform_watershadow;
    public int uniform_shadowtex0;
    public int uniform_shadowtex1;
    public int uniform_depthtex0;
    public int uniform_depthtex1;
    public int uniform_shadowcolor;
    public int uniform_shadowcolor0;
    public int uniform_shadowcolor1;
    public int uniform_noisetex;
    public int uniform_gcolor;
    public int uniform_gdepth;
    public int uniform_gnormal;
    public int uniform_composite;
    public int uniform_gaux1;
    public int uniform_gaux2;
    public int uniform_gaux3;
    public int uniform_gaux4;
    public int uniform_colortex0;
    public int uniform_colortex1;
    public int uniform_colortex2;
    public int uniform_colortex3;
    public int uniform_colortex4;
    public int uniform_colortex5;
    public int uniform_colortex6;
    public int uniform_colortex7;
    public int uniform_gdepthtex;
    public int uniform_depthtex2;
    public int uniform_tex;
    public int uniform_heldItemId;
    public int uniform_heldBlockLightValue;
    public int uniform_fogMode;
    public int uniform_fogColor;
    public int uniform_skyColor;
    public int uniform_worldTime;
    public int uniform_moonPhase;
    public int uniform_frameTimeCounter;
    public int uniform_sunAngle;
    public int uniform_shadowAngle;
    public int uniform_rainStrength;
    public int uniform_aspectRatio;
    public int uniform_viewWidth;
    public int uniform_viewHeight;
    public int uniform_near;
    public int uniform_far;
    public int uniform_sunPosition;
    public int uniform_moonPosition;
    public int uniform_upPosition;
    public int uniform_previousCameraPosition;
    public int uniform_cameraPosition;
    public int uniform_gbufferModelView;
    public int uniform_gbufferModelViewInverse;
    public int uniform_gbufferPreviousProjection;
    public int uniform_gbufferProjection;
    public int uniform_gbufferProjectionInverse;
    public int uniform_gbufferPreviousModelView;
    public int uniform_shadowProjection;
    public int uniform_shadowProjectionInverse;
    public int uniform_shadowModelView;
    public int uniform_shadowModelViewInverse;
    public int uniform_wetness;
    public int uniform_eyeAltitude;
    public int uniform_eyeBrightness;
    public int uniform_eyeBrightnessSmooth;
    public int uniform_terrainTextureSize;
    public int uniform_terrainIconSize;
    public int uniform_isEyeInWater;
    public int uniform_hideGUI;
    public int uniform_centerDepthSmooth;
    public int uniform_atlasSize;
    
    public ShaderProgramData(final int programIDGL) {
        this.programIDGL = programIDGL;
        this.uniform_texture = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "texture");
        this.uniform_lightmap = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "lightmap");
        this.uniform_normals = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "normals");
        this.uniform_specular = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "specular");
        this.uniform_shadow = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadow");
        this.uniform_watershadow = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "watershadow");
        this.uniform_shadowtex0 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowtex0");
        this.uniform_shadowtex1 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowtex1");
        this.uniform_depthtex0 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "depthtex0");
        this.uniform_depthtex1 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "depthtex1");
        this.uniform_shadowcolor = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowcolor");
        this.uniform_shadowcolor0 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowcolor0");
        this.uniform_shadowcolor1 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowcolor1");
        this.uniform_noisetex = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "noisetex");
        this.uniform_gcolor = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gcolor");
        this.uniform_gdepth = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gdepth");
        this.uniform_gnormal = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gnormal");
        this.uniform_composite = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "composite");
        this.uniform_gaux1 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gaux1");
        this.uniform_gaux2 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gaux2");
        this.uniform_gaux3 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gaux3");
        this.uniform_gaux4 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gaux4");
        this.uniform_colortex0 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "colortex0");
        this.uniform_colortex1 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "colortex1");
        this.uniform_colortex2 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "colortex2");
        this.uniform_colortex3 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "colortex3");
        this.uniform_colortex4 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "colortex4");
        this.uniform_colortex5 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "colortex5");
        this.uniform_colortex6 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "colortex6");
        this.uniform_colortex7 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "colortex7");
        this.uniform_gdepthtex = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gdepthtex");
        this.uniform_depthtex2 = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "depthtex2");
        this.uniform_tex = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "tex");
        this.uniform_heldItemId = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "heldItemId");
        this.uniform_heldBlockLightValue = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "heldBlockLightValue");
        this.uniform_fogMode = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "fogMode");
        this.uniform_fogColor = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "fogColor");
        this.uniform_skyColor = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "skyColor");
        this.uniform_worldTime = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "worldTime");
        this.uniform_moonPhase = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "moonPhase");
        this.uniform_frameTimeCounter = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "frameTimeCounter");
        this.uniform_sunAngle = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "sunAngle");
        this.uniform_shadowAngle = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowAngle");
        this.uniform_rainStrength = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "rainStrength");
        this.uniform_aspectRatio = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "aspectRatio");
        this.uniform_viewWidth = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "viewWidth");
        this.uniform_viewHeight = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "viewHeight");
        this.uniform_near = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "near");
        this.uniform_far = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "far");
        this.uniform_sunPosition = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "sunPosition");
        this.uniform_moonPosition = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "moonPosition");
        this.uniform_upPosition = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "upPosition");
        this.uniform_previousCameraPosition = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "previousCameraPosition");
        this.uniform_cameraPosition = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "cameraPosition");
        this.uniform_gbufferModelView = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gbufferModelView");
        this.uniform_gbufferModelViewInverse = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gbufferModelViewInverse");
        this.uniform_gbufferPreviousProjection = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gbufferPreviousProjection");
        this.uniform_gbufferProjection = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gbufferProjection");
        this.uniform_gbufferProjectionInverse = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gbufferProjectionInverse");
        this.uniform_gbufferPreviousModelView = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "gbufferPreviousModelView");
        this.uniform_shadowProjection = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowProjection");
        this.uniform_shadowProjectionInverse = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowProjectionInverse");
        this.uniform_shadowModelView = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowModelView");
        this.uniform_shadowModelViewInverse = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "shadowModelViewInverse");
        this.uniform_wetness = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "wetness");
        this.uniform_eyeAltitude = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "eyeAltitude");
        this.uniform_eyeBrightness = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "eyeBrightness");
        this.uniform_eyeBrightnessSmooth = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "eyeBrightnessSmooth");
        this.uniform_terrainTextureSize = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "terrainTextureSize");
        this.uniform_terrainIconSize = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "terrainIconSize");
        this.uniform_isEyeInWater = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "isEyeInWater");
        this.uniform_hideGUI = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "hideGUI");
        this.uniform_centerDepthSmooth = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "centerDepthSmooth");
        this.uniform_atlasSize = ARBShaderObjects.glGetUniformLocationARB(programIDGL, "atlasSize");
    }
}