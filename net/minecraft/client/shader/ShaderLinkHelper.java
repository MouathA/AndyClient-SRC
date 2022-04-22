package net.minecraft.client.shader;

import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.util.*;

public class ShaderLinkHelper
{
    private static final Logger logger;
    private static ShaderLinkHelper staticShaderLinkHelper;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001045";
        logger = LogManager.getLogger();
    }
    
    public static void setNewStaticShaderLinkHelper() {
        ShaderLinkHelper.staticShaderLinkHelper = new ShaderLinkHelper();
    }
    
    public static ShaderLinkHelper getStaticShaderLinkHelper() {
        return ShaderLinkHelper.staticShaderLinkHelper;
    }
    
    public void deleteShader(final ShaderManager shaderManager) {
        shaderManager.getFragmentShaderLoader().deleteShader(shaderManager);
        shaderManager.getVertexShaderLoader().deleteShader(shaderManager);
        OpenGlHelper.glDeleteProgram(shaderManager.getProgram());
    }
    
    public int createProgram() throws JsonException {
        final int glCreateProgram = OpenGlHelper.glCreateProgram();
        if (glCreateProgram <= 0) {
            throw new JsonException("Could not create shader program (returned program ID " + glCreateProgram + ")");
        }
        return glCreateProgram;
    }
    
    public void linkProgram(final ShaderManager shaderManager) {
        shaderManager.getFragmentShaderLoader().attachShader(shaderManager);
        shaderManager.getVertexShaderLoader().attachShader(shaderManager);
        OpenGlHelper.glLinkProgram(shaderManager.getProgram());
        if (OpenGlHelper.glGetProgrami(shaderManager.getProgram(), OpenGlHelper.GL_LINK_STATUS) == 0) {
            ShaderLinkHelper.logger.warn("Error encountered when linking program containing VS " + shaderManager.getVertexShaderLoader().getShaderFilename() + " and FS " + shaderManager.getFragmentShaderLoader().getShaderFilename() + ". Log output:");
            ShaderLinkHelper.logger.warn(OpenGlHelper.glGetProgramInfoLog(shaderManager.getProgram(), 32768));
        }
    }
}
