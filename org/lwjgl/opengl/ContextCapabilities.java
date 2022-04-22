package org.lwjgl.opengl;

import java.util.*;
import org.lwjgl.*;

public class ContextCapabilities
{
    static final boolean DEBUG = false;
    final APIUtil util;
    final StateTracker tracker;
    public final boolean GL_AMD_blend_minmax_factor;
    public final boolean GL_AMD_conservative_depth;
    public final boolean GL_AMD_debug_output;
    public final boolean GL_AMD_depth_clamp_separate;
    public final boolean GL_AMD_draw_buffers_blend;
    public final boolean GL_AMD_interleaved_elements;
    public final boolean GL_AMD_multi_draw_indirect;
    public final boolean GL_AMD_name_gen_delete;
    public final boolean GL_AMD_performance_monitor;
    public final boolean GL_AMD_pinned_memory;
    public final boolean GL_AMD_query_buffer_object;
    public final boolean GL_AMD_sample_positions;
    public final boolean GL_AMD_seamless_cubemap_per_texture;
    public final boolean GL_AMD_shader_atomic_counter_ops;
    public final boolean GL_AMD_shader_stencil_export;
    public final boolean GL_AMD_shader_trinary_minmax;
    public final boolean GL_AMD_sparse_texture;
    public final boolean GL_AMD_stencil_operation_extended;
    public final boolean GL_AMD_texture_texture4;
    public final boolean GL_AMD_transform_feedback3_lines_triangles;
    public final boolean GL_AMD_vertex_shader_layer;
    public final boolean GL_AMD_vertex_shader_tessellator;
    public final boolean GL_AMD_vertex_shader_viewport_index;
    public final boolean GL_APPLE_aux_depth_stencil;
    public final boolean GL_APPLE_client_storage;
    public final boolean GL_APPLE_element_array;
    public final boolean GL_APPLE_fence;
    public final boolean GL_APPLE_float_pixels;
    public final boolean GL_APPLE_flush_buffer_range;
    public final boolean GL_APPLE_object_purgeable;
    public final boolean GL_APPLE_packed_pixels;
    public final boolean GL_APPLE_rgb_422;
    public final boolean GL_APPLE_row_bytes;
    public final boolean GL_APPLE_texture_range;
    public final boolean GL_APPLE_vertex_array_object;
    public final boolean GL_APPLE_vertex_array_range;
    public final boolean GL_APPLE_vertex_program_evaluators;
    public final boolean GL_APPLE_ycbcr_422;
    public final boolean GL_ARB_ES2_compatibility;
    public final boolean GL_ARB_ES3_1_compatibility;
    public final boolean GL_ARB_ES3_compatibility;
    public final boolean GL_ARB_arrays_of_arrays;
    public final boolean GL_ARB_base_instance;
    public final boolean GL_ARB_bindless_texture;
    public final boolean GL_ARB_blend_func_extended;
    public final boolean GL_ARB_buffer_storage;
    public final boolean GL_ARB_cl_event;
    public final boolean GL_ARB_clear_buffer_object;
    public final boolean GL_ARB_clear_texture;
    public final boolean GL_ARB_clip_control;
    public final boolean GL_ARB_color_buffer_float;
    public final boolean GL_ARB_compatibility;
    public final boolean GL_ARB_compressed_texture_pixel_storage;
    public final boolean GL_ARB_compute_shader;
    public final boolean GL_ARB_compute_variable_group_size;
    public final boolean GL_ARB_conditional_render_inverted;
    public final boolean GL_ARB_conservative_depth;
    public final boolean GL_ARB_copy_buffer;
    public final boolean GL_ARB_copy_image;
    public final boolean GL_ARB_cull_distance;
    public final boolean GL_ARB_debug_output;
    public final boolean GL_ARB_depth_buffer_float;
    public final boolean GL_ARB_depth_clamp;
    public final boolean GL_ARB_depth_texture;
    public final boolean GL_ARB_derivative_control;
    public final boolean GL_ARB_direct_state_access;
    public final boolean GL_ARB_draw_buffers;
    public final boolean GL_ARB_draw_buffers_blend;
    public final boolean GL_ARB_draw_elements_base_vertex;
    public final boolean GL_ARB_draw_indirect;
    public final boolean GL_ARB_draw_instanced;
    public final boolean GL_ARB_enhanced_layouts;
    public final boolean GL_ARB_explicit_attrib_location;
    public final boolean GL_ARB_explicit_uniform_location;
    public final boolean GL_ARB_fragment_coord_conventions;
    public final boolean GL_ARB_fragment_layer_viewport;
    public final boolean GL_ARB_fragment_program;
    public final boolean GL_ARB_fragment_program_shadow;
    public final boolean GL_ARB_fragment_shader;
    public final boolean GL_ARB_framebuffer_no_attachments;
    public final boolean GL_ARB_framebuffer_object;
    public final boolean GL_ARB_framebuffer_sRGB;
    public final boolean GL_ARB_geometry_shader4;
    public final boolean GL_ARB_get_program_binary;
    public final boolean GL_ARB_get_texture_sub_image;
    public final boolean GL_ARB_gpu_shader5;
    public final boolean GL_ARB_gpu_shader_fp64;
    public final boolean GL_ARB_half_float_pixel;
    public final boolean GL_ARB_half_float_vertex;
    public final boolean GL_ARB_imaging;
    public final boolean GL_ARB_indirect_parameters;
    public final boolean GL_ARB_instanced_arrays;
    public final boolean GL_ARB_internalformat_query;
    public final boolean GL_ARB_internalformat_query2;
    public final boolean GL_ARB_invalidate_subdata;
    public final boolean GL_ARB_map_buffer_alignment;
    public final boolean GL_ARB_map_buffer_range;
    public final boolean GL_ARB_matrix_palette;
    public final boolean GL_ARB_multi_bind;
    public final boolean GL_ARB_multi_draw_indirect;
    public final boolean GL_ARB_multisample;
    public final boolean GL_ARB_multitexture;
    public final boolean GL_ARB_occlusion_query;
    public final boolean GL_ARB_occlusion_query2;
    public final boolean GL_ARB_pipeline_statistics_query;
    public final boolean GL_ARB_pixel_buffer_object;
    public final boolean GL_ARB_point_parameters;
    public final boolean GL_ARB_point_sprite;
    public final boolean GL_ARB_program_interface_query;
    public final boolean GL_ARB_provoking_vertex;
    public final boolean GL_ARB_query_buffer_object;
    public final boolean GL_ARB_robust_buffer_access_behavior;
    public final boolean GL_ARB_robustness;
    public final boolean GL_ARB_robustness_isolation;
    public final boolean GL_ARB_sample_shading;
    public final boolean GL_ARB_sampler_objects;
    public final boolean GL_ARB_seamless_cube_map;
    public final boolean GL_ARB_seamless_cubemap_per_texture;
    public final boolean GL_ARB_separate_shader_objects;
    public final boolean GL_ARB_shader_atomic_counters;
    public final boolean GL_ARB_shader_bit_encoding;
    public final boolean GL_ARB_shader_draw_parameters;
    public final boolean GL_ARB_shader_group_vote;
    public final boolean GL_ARB_shader_image_load_store;
    public final boolean GL_ARB_shader_image_size;
    public final boolean GL_ARB_shader_objects;
    public final boolean GL_ARB_shader_precision;
    public final boolean GL_ARB_shader_stencil_export;
    public final boolean GL_ARB_shader_storage_buffer_object;
    public final boolean GL_ARB_shader_subroutine;
    public final boolean GL_ARB_shader_texture_image_samples;
    public final boolean GL_ARB_shader_texture_lod;
    public final boolean GL_ARB_shading_language_100;
    public final boolean GL_ARB_shading_language_420pack;
    public final boolean GL_ARB_shading_language_include;
    public final boolean GL_ARB_shading_language_packing;
    public final boolean GL_ARB_shadow;
    public final boolean GL_ARB_shadow_ambient;
    public final boolean GL_ARB_sparse_buffer;
    public final boolean GL_ARB_sparse_texture;
    public final boolean GL_ARB_stencil_texturing;
    public final boolean GL_ARB_sync;
    public final boolean GL_ARB_tessellation_shader;
    public final boolean GL_ARB_texture_barrier;
    public final boolean GL_ARB_texture_border_clamp;
    public final boolean GL_ARB_texture_buffer_object;
    public final boolean GL_ARB_texture_buffer_object_rgb32;
    public final boolean GL_ARB_texture_buffer_range;
    public final boolean GL_ARB_texture_compression;
    public final boolean GL_ARB_texture_compression_bptc;
    public final boolean GL_ARB_texture_compression_rgtc;
    public final boolean GL_ARB_texture_cube_map;
    public final boolean GL_ARB_texture_cube_map_array;
    public final boolean GL_ARB_texture_env_add;
    public final boolean GL_ARB_texture_env_combine;
    public final boolean GL_ARB_texture_env_crossbar;
    public final boolean GL_ARB_texture_env_dot3;
    public final boolean GL_ARB_texture_float;
    public final boolean GL_ARB_texture_gather;
    public final boolean GL_ARB_texture_mirror_clamp_to_edge;
    public final boolean GL_ARB_texture_mirrored_repeat;
    public final boolean GL_ARB_texture_multisample;
    public final boolean GL_ARB_texture_non_power_of_two;
    public final boolean GL_ARB_texture_query_levels;
    public final boolean GL_ARB_texture_query_lod;
    public final boolean GL_ARB_texture_rectangle;
    public final boolean GL_ARB_texture_rg;
    public final boolean GL_ARB_texture_rgb10_a2ui;
    public final boolean GL_ARB_texture_stencil8;
    public final boolean GL_ARB_texture_storage;
    public final boolean GL_ARB_texture_storage_multisample;
    public final boolean GL_ARB_texture_swizzle;
    public final boolean GL_ARB_texture_view;
    public final boolean GL_ARB_timer_query;
    public final boolean GL_ARB_transform_feedback2;
    public final boolean GL_ARB_transform_feedback3;
    public final boolean GL_ARB_transform_feedback_instanced;
    public final boolean GL_ARB_transform_feedback_overflow_query;
    public final boolean GL_ARB_transpose_matrix;
    public final boolean GL_ARB_uniform_buffer_object;
    public final boolean GL_ARB_vertex_array_bgra;
    public final boolean GL_ARB_vertex_array_object;
    public final boolean GL_ARB_vertex_attrib_64bit;
    public final boolean GL_ARB_vertex_attrib_binding;
    public final boolean GL_ARB_vertex_blend;
    public final boolean GL_ARB_vertex_buffer_object;
    public final boolean GL_ARB_vertex_program;
    public final boolean GL_ARB_vertex_shader;
    public final boolean GL_ARB_vertex_type_10f_11f_11f_rev;
    public final boolean GL_ARB_vertex_type_2_10_10_10_rev;
    public final boolean GL_ARB_viewport_array;
    public final boolean GL_ARB_window_pos;
    public final boolean GL_ATI_draw_buffers;
    public final boolean GL_ATI_element_array;
    public final boolean GL_ATI_envmap_bumpmap;
    public final boolean GL_ATI_fragment_shader;
    public final boolean GL_ATI_map_object_buffer;
    public final boolean GL_ATI_meminfo;
    public final boolean GL_ATI_pn_triangles;
    public final boolean GL_ATI_separate_stencil;
    public final boolean GL_ATI_shader_texture_lod;
    public final boolean GL_ATI_text_fragment_shader;
    public final boolean GL_ATI_texture_compression_3dc;
    public final boolean GL_ATI_texture_env_combine3;
    public final boolean GL_ATI_texture_float;
    public final boolean GL_ATI_texture_mirror_once;
    public final boolean GL_ATI_vertex_array_object;
    public final boolean GL_ATI_vertex_attrib_array_object;
    public final boolean GL_ATI_vertex_streams;
    public final boolean GL_EXT_Cg_shader;
    public final boolean GL_EXT_abgr;
    public final boolean GL_EXT_bgra;
    public final boolean GL_EXT_bindable_uniform;
    public final boolean GL_EXT_blend_color;
    public final boolean GL_EXT_blend_equation_separate;
    public final boolean GL_EXT_blend_func_separate;
    public final boolean GL_EXT_blend_minmax;
    public final boolean GL_EXT_blend_subtract;
    public final boolean GL_EXT_compiled_vertex_array;
    public final boolean GL_EXT_depth_bounds_test;
    public final boolean GL_EXT_direct_state_access;
    public final boolean GL_EXT_draw_buffers2;
    public final boolean GL_EXT_draw_instanced;
    public final boolean GL_EXT_draw_range_elements;
    public final boolean GL_EXT_fog_coord;
    public final boolean GL_EXT_framebuffer_blit;
    public final boolean GL_EXT_framebuffer_multisample;
    public final boolean GL_EXT_framebuffer_multisample_blit_scaled;
    public final boolean GL_EXT_framebuffer_object;
    public final boolean GL_EXT_framebuffer_sRGB;
    public final boolean GL_EXT_geometry_shader4;
    public final boolean GL_EXT_gpu_program_parameters;
    public final boolean GL_EXT_gpu_shader4;
    public final boolean GL_EXT_multi_draw_arrays;
    public final boolean GL_EXT_packed_depth_stencil;
    public final boolean GL_EXT_packed_float;
    public final boolean GL_EXT_packed_pixels;
    public final boolean GL_EXT_paletted_texture;
    public final boolean GL_EXT_pixel_buffer_object;
    public final boolean GL_EXT_point_parameters;
    public final boolean GL_EXT_provoking_vertex;
    public final boolean GL_EXT_rescale_normal;
    public final boolean GL_EXT_secondary_color;
    public final boolean GL_EXT_separate_shader_objects;
    public final boolean GL_EXT_separate_specular_color;
    public final boolean GL_EXT_shader_image_load_store;
    public final boolean GL_EXT_shadow_funcs;
    public final boolean GL_EXT_shared_texture_palette;
    public final boolean GL_EXT_stencil_clear_tag;
    public final boolean GL_EXT_stencil_two_side;
    public final boolean GL_EXT_stencil_wrap;
    public final boolean GL_EXT_texture_3d;
    public final boolean GL_EXT_texture_array;
    public final boolean GL_EXT_texture_buffer_object;
    public final boolean GL_EXT_texture_compression_latc;
    public final boolean GL_EXT_texture_compression_rgtc;
    public final boolean GL_EXT_texture_compression_s3tc;
    public final boolean GL_EXT_texture_env_combine;
    public final boolean GL_EXT_texture_env_dot3;
    public final boolean GL_EXT_texture_filter_anisotropic;
    public final boolean GL_EXT_texture_integer;
    public final boolean GL_EXT_texture_lod_bias;
    public final boolean GL_EXT_texture_mirror_clamp;
    public final boolean GL_EXT_texture_rectangle;
    public final boolean GL_EXT_texture_sRGB;
    public final boolean GL_EXT_texture_sRGB_decode;
    public final boolean GL_EXT_texture_shared_exponent;
    public final boolean GL_EXT_texture_snorm;
    public final boolean GL_EXT_texture_swizzle;
    public final boolean GL_EXT_timer_query;
    public final boolean GL_EXT_transform_feedback;
    public final boolean GL_EXT_vertex_array_bgra;
    public final boolean GL_EXT_vertex_attrib_64bit;
    public final boolean GL_EXT_vertex_shader;
    public final boolean GL_EXT_vertex_weighting;
    public final boolean OpenGL11;
    public final boolean OpenGL12;
    public final boolean OpenGL13;
    public final boolean OpenGL14;
    public final boolean OpenGL15;
    public final boolean OpenGL20;
    public final boolean OpenGL21;
    public final boolean OpenGL30;
    public final boolean OpenGL31;
    public final boolean OpenGL32;
    public final boolean OpenGL33;
    public final boolean OpenGL40;
    public final boolean OpenGL41;
    public final boolean OpenGL42;
    public final boolean OpenGL43;
    public final boolean OpenGL44;
    public final boolean OpenGL45;
    public final boolean GL_GREMEDY_frame_terminator;
    public final boolean GL_GREMEDY_string_marker;
    public final boolean GL_HP_occlusion_test;
    public final boolean GL_IBM_rasterpos_clip;
    public final boolean GL_INTEL_map_texture;
    public final boolean GL_KHR_context_flush_control;
    public final boolean GL_KHR_debug;
    public final boolean GL_KHR_robust_buffer_access_behavior;
    public final boolean GL_KHR_robustness;
    public final boolean GL_KHR_texture_compression_astc_ldr;
    public final boolean GL_NVX_gpu_memory_info;
    public final boolean GL_NV_bindless_multi_draw_indirect;
    public final boolean GL_NV_bindless_texture;
    public final boolean GL_NV_blend_equation_advanced;
    public final boolean GL_NV_blend_square;
    public final boolean GL_NV_compute_program5;
    public final boolean GL_NV_conditional_render;
    public final boolean GL_NV_copy_depth_to_color;
    public final boolean GL_NV_copy_image;
    public final boolean GL_NV_deep_texture3D;
    public final boolean GL_NV_depth_buffer_float;
    public final boolean GL_NV_depth_clamp;
    public final boolean GL_NV_draw_texture;
    public final boolean GL_NV_evaluators;
    public final boolean GL_NV_explicit_multisample;
    public final boolean GL_NV_fence;
    public final boolean GL_NV_float_buffer;
    public final boolean GL_NV_fog_distance;
    public final boolean GL_NV_fragment_program;
    public final boolean GL_NV_fragment_program2;
    public final boolean GL_NV_fragment_program4;
    public final boolean GL_NV_fragment_program_option;
    public final boolean GL_NV_framebuffer_multisample_coverage;
    public final boolean GL_NV_geometry_program4;
    public final boolean GL_NV_geometry_shader4;
    public final boolean GL_NV_gpu_program4;
    public final boolean GL_NV_gpu_program5;
    public final boolean GL_NV_gpu_program5_mem_extended;
    public final boolean GL_NV_gpu_shader5;
    public final boolean GL_NV_half_float;
    public final boolean GL_NV_light_max_exponent;
    public final boolean GL_NV_multisample_coverage;
    public final boolean GL_NV_multisample_filter_hint;
    public final boolean GL_NV_occlusion_query;
    public final boolean GL_NV_packed_depth_stencil;
    public final boolean GL_NV_parameter_buffer_object;
    public final boolean GL_NV_parameter_buffer_object2;
    public final boolean GL_NV_path_rendering;
    public final boolean GL_NV_pixel_data_range;
    public final boolean GL_NV_point_sprite;
    public final boolean GL_NV_present_video;
    public final boolean GL_NV_primitive_restart;
    public final boolean GL_NV_register_combiners;
    public final boolean GL_NV_register_combiners2;
    public final boolean GL_NV_shader_atomic_counters;
    public final boolean GL_NV_shader_atomic_float;
    public final boolean GL_NV_shader_buffer_load;
    public final boolean GL_NV_shader_buffer_store;
    public final boolean GL_NV_shader_storage_buffer_object;
    public final boolean GL_NV_tessellation_program5;
    public final boolean GL_NV_texgen_reflection;
    public final boolean GL_NV_texture_barrier;
    public final boolean GL_NV_texture_compression_vtc;
    public final boolean GL_NV_texture_env_combine4;
    public final boolean GL_NV_texture_expand_normal;
    public final boolean GL_NV_texture_multisample;
    public final boolean GL_NV_texture_rectangle;
    public final boolean GL_NV_texture_shader;
    public final boolean GL_NV_texture_shader2;
    public final boolean GL_NV_texture_shader3;
    public final boolean GL_NV_transform_feedback;
    public final boolean GL_NV_transform_feedback2;
    public final boolean GL_NV_vertex_array_range;
    public final boolean GL_NV_vertex_array_range2;
    public final boolean GL_NV_vertex_attrib_integer_64bit;
    public final boolean GL_NV_vertex_buffer_unified_memory;
    public final boolean GL_NV_vertex_program;
    public final boolean GL_NV_vertex_program1_1;
    public final boolean GL_NV_vertex_program2;
    public final boolean GL_NV_vertex_program2_option;
    public final boolean GL_NV_vertex_program3;
    public final boolean GL_NV_vertex_program4;
    public final boolean GL_NV_video_capture;
    public final boolean GL_SGIS_generate_mipmap;
    public final boolean GL_SGIS_texture_lod;
    public final boolean GL_SUN_slice_accum;
    long glDebugMessageEnableAMD;
    long glDebugMessageInsertAMD;
    long glDebugMessageCallbackAMD;
    long glGetDebugMessageLogAMD;
    long glBlendFuncIndexedAMD;
    long glBlendFuncSeparateIndexedAMD;
    long glBlendEquationIndexedAMD;
    long glBlendEquationSeparateIndexedAMD;
    long glVertexAttribParameteriAMD;
    long glMultiDrawArraysIndirectAMD;
    long glMultiDrawElementsIndirectAMD;
    long glGenNamesAMD;
    long glDeleteNamesAMD;
    long glIsNameAMD;
    long glGetPerfMonitorGroupsAMD;
    long glGetPerfMonitorCountersAMD;
    long glGetPerfMonitorGroupStringAMD;
    long glGetPerfMonitorCounterStringAMD;
    long glGetPerfMonitorCounterInfoAMD;
    long glGenPerfMonitorsAMD;
    long glDeletePerfMonitorsAMD;
    long glSelectPerfMonitorCountersAMD;
    long glBeginPerfMonitorAMD;
    long glEndPerfMonitorAMD;
    long glGetPerfMonitorCounterDataAMD;
    long glSetMultisamplefvAMD;
    long glTexStorageSparseAMD;
    long glTextureStorageSparseAMD;
    long glStencilOpValueAMD;
    long glTessellationFactorAMD;
    long glTessellationModeAMD;
    long glElementPointerAPPLE;
    long glDrawElementArrayAPPLE;
    long glDrawRangeElementArrayAPPLE;
    long glMultiDrawElementArrayAPPLE;
    long glMultiDrawRangeElementArrayAPPLE;
    long glGenFencesAPPLE;
    long glDeleteFencesAPPLE;
    long glSetFenceAPPLE;
    long glIsFenceAPPLE;
    long glTestFenceAPPLE;
    long glFinishFenceAPPLE;
    long glTestObjectAPPLE;
    long glFinishObjectAPPLE;
    long glBufferParameteriAPPLE;
    long glFlushMappedBufferRangeAPPLE;
    long glObjectPurgeableAPPLE;
    long glObjectUnpurgeableAPPLE;
    long glGetObjectParameterivAPPLE;
    long glTextureRangeAPPLE;
    long glGetTexParameterPointervAPPLE;
    long glBindVertexArrayAPPLE;
    long glDeleteVertexArraysAPPLE;
    long glGenVertexArraysAPPLE;
    long glIsVertexArrayAPPLE;
    long glVertexArrayRangeAPPLE;
    long glFlushVertexArrayRangeAPPLE;
    long glVertexArrayParameteriAPPLE;
    long glEnableVertexAttribAPPLE;
    long glDisableVertexAttribAPPLE;
    long glIsVertexAttribEnabledAPPLE;
    long glMapVertexAttrib1dAPPLE;
    long glMapVertexAttrib1fAPPLE;
    long glMapVertexAttrib2dAPPLE;
    long glMapVertexAttrib2fAPPLE;
    long glGetTextureHandleARB;
    long glGetTextureSamplerHandleARB;
    long glMakeTextureHandleResidentARB;
    long glMakeTextureHandleNonResidentARB;
    long glGetImageHandleARB;
    long glMakeImageHandleResidentARB;
    long glMakeImageHandleNonResidentARB;
    long glUniformHandleui64ARB;
    long glUniformHandleui64vARB;
    long glProgramUniformHandleui64ARB;
    long glProgramUniformHandleui64vARB;
    long glIsTextureHandleResidentARB;
    long glIsImageHandleResidentARB;
    long glVertexAttribL1ui64ARB;
    long glVertexAttribL1ui64vARB;
    long glGetVertexAttribLui64vARB;
    long glBindBufferARB;
    long glDeleteBuffersARB;
    long glGenBuffersARB;
    long glIsBufferARB;
    long glBufferDataARB;
    long glBufferSubDataARB;
    long glGetBufferSubDataARB;
    long glMapBufferARB;
    long glUnmapBufferARB;
    long glGetBufferParameterivARB;
    long glGetBufferPointervARB;
    long glNamedBufferStorageEXT;
    long glCreateSyncFromCLeventARB;
    long glClearNamedBufferDataEXT;
    long glClearNamedBufferSubDataEXT;
    long glClampColorARB;
    long glDispatchComputeGroupSizeARB;
    long glDebugMessageControlARB;
    long glDebugMessageInsertARB;
    long glDebugMessageCallbackARB;
    long glGetDebugMessageLogARB;
    long glDrawBuffersARB;
    long glBlendEquationiARB;
    long glBlendEquationSeparateiARB;
    long glBlendFunciARB;
    long glBlendFuncSeparateiARB;
    long glDrawArraysInstancedARB;
    long glDrawElementsInstancedARB;
    long glNamedFramebufferParameteriEXT;
    long glGetNamedFramebufferParameterivEXT;
    long glProgramParameteriARB;
    long glFramebufferTextureARB;
    long glFramebufferTextureLayerARB;
    long glFramebufferTextureFaceARB;
    long glProgramUniform1dEXT;
    long glProgramUniform2dEXT;
    long glProgramUniform3dEXT;
    long glProgramUniform4dEXT;
    long glProgramUniform1dvEXT;
    long glProgramUniform2dvEXT;
    long glProgramUniform3dvEXT;
    long glProgramUniform4dvEXT;
    long glProgramUniformMatrix2dvEXT;
    long glProgramUniformMatrix3dvEXT;
    long glProgramUniformMatrix4dvEXT;
    long glProgramUniformMatrix2x3dvEXT;
    long glProgramUniformMatrix2x4dvEXT;
    long glProgramUniformMatrix3x2dvEXT;
    long glProgramUniformMatrix3x4dvEXT;
    long glProgramUniformMatrix4x2dvEXT;
    long glProgramUniformMatrix4x3dvEXT;
    long glColorTable;
    long glColorSubTable;
    long glColorTableParameteriv;
    long glColorTableParameterfv;
    long glCopyColorSubTable;
    long glCopyColorTable;
    long glGetColorTable;
    long glGetColorTableParameteriv;
    long glGetColorTableParameterfv;
    long glHistogram;
    long glResetHistogram;
    long glGetHistogram;
    long glGetHistogramParameterfv;
    long glGetHistogramParameteriv;
    long glMinmax;
    long glResetMinmax;
    long glGetMinmax;
    long glGetMinmaxParameterfv;
    long glGetMinmaxParameteriv;
    long glConvolutionFilter1D;
    long glConvolutionFilter2D;
    long glConvolutionParameterf;
    long glConvolutionParameterfv;
    long glConvolutionParameteri;
    long glConvolutionParameteriv;
    long glCopyConvolutionFilter1D;
    long glCopyConvolutionFilter2D;
    long glGetConvolutionFilter;
    long glGetConvolutionParameterfv;
    long glGetConvolutionParameteriv;
    long glSeparableFilter2D;
    long glGetSeparableFilter;
    long glMultiDrawArraysIndirectCountARB;
    long glMultiDrawElementsIndirectCountARB;
    long glVertexAttribDivisorARB;
    long glCurrentPaletteMatrixARB;
    long glMatrixIndexPointerARB;
    long glMatrixIndexubvARB;
    long glMatrixIndexusvARB;
    long glMatrixIndexuivARB;
    long glSampleCoverageARB;
    long glClientActiveTextureARB;
    long glActiveTextureARB;
    long glMultiTexCoord1fARB;
    long glMultiTexCoord1dARB;
    long glMultiTexCoord1iARB;
    long glMultiTexCoord1sARB;
    long glMultiTexCoord2fARB;
    long glMultiTexCoord2dARB;
    long glMultiTexCoord2iARB;
    long glMultiTexCoord2sARB;
    long glMultiTexCoord3fARB;
    long glMultiTexCoord3dARB;
    long glMultiTexCoord3iARB;
    long glMultiTexCoord3sARB;
    long glMultiTexCoord4fARB;
    long glMultiTexCoord4dARB;
    long glMultiTexCoord4iARB;
    long glMultiTexCoord4sARB;
    long glGenQueriesARB;
    long glDeleteQueriesARB;
    long glIsQueryARB;
    long glBeginQueryARB;
    long glEndQueryARB;
    long glGetQueryivARB;
    long glGetQueryObjectivARB;
    long glGetQueryObjectuivARB;
    long glPointParameterfARB;
    long glPointParameterfvARB;
    long glProgramStringARB;
    long glBindProgramARB;
    long glDeleteProgramsARB;
    long glGenProgramsARB;
    long glProgramEnvParameter4fARB;
    long glProgramEnvParameter4dARB;
    long glProgramEnvParameter4fvARB;
    long glProgramEnvParameter4dvARB;
    long glProgramLocalParameter4fARB;
    long glProgramLocalParameter4dARB;
    long glProgramLocalParameter4fvARB;
    long glProgramLocalParameter4dvARB;
    long glGetProgramEnvParameterfvARB;
    long glGetProgramEnvParameterdvARB;
    long glGetProgramLocalParameterfvARB;
    long glGetProgramLocalParameterdvARB;
    long glGetProgramivARB;
    long glGetProgramStringARB;
    long glIsProgramARB;
    long glGetGraphicsResetStatusARB;
    long glGetnMapdvARB;
    long glGetnMapfvARB;
    long glGetnMapivARB;
    long glGetnPixelMapfvARB;
    long glGetnPixelMapuivARB;
    long glGetnPixelMapusvARB;
    long glGetnPolygonStippleARB;
    long glGetnTexImageARB;
    long glReadnPixelsARB;
    long glGetnColorTableARB;
    long glGetnConvolutionFilterARB;
    long glGetnSeparableFilterARB;
    long glGetnHistogramARB;
    long glGetnMinmaxARB;
    long glGetnCompressedTexImageARB;
    long glGetnUniformfvARB;
    long glGetnUniformivARB;
    long glGetnUniformuivARB;
    long glGetnUniformdvARB;
    long glMinSampleShadingARB;
    long glDeleteObjectARB;
    long glGetHandleARB;
    long glDetachObjectARB;
    long glCreateShaderObjectARB;
    long glShaderSourceARB;
    long glCompileShaderARB;
    long glCreateProgramObjectARB;
    long glAttachObjectARB;
    long glLinkProgramARB;
    long glUseProgramObjectARB;
    long glValidateProgramARB;
    long glUniform1fARB;
    long glUniform2fARB;
    long glUniform3fARB;
    long glUniform4fARB;
    long glUniform1iARB;
    long glUniform2iARB;
    long glUniform3iARB;
    long glUniform4iARB;
    long glUniform1fvARB;
    long glUniform2fvARB;
    long glUniform3fvARB;
    long glUniform4fvARB;
    long glUniform1ivARB;
    long glUniform2ivARB;
    long glUniform3ivARB;
    long glUniform4ivARB;
    long glUniformMatrix2fvARB;
    long glUniformMatrix3fvARB;
    long glUniformMatrix4fvARB;
    long glGetObjectParameterfvARB;
    long glGetObjectParameterivARB;
    long glGetInfoLogARB;
    long glGetAttachedObjectsARB;
    long glGetUniformLocationARB;
    long glGetActiveUniformARB;
    long glGetUniformfvARB;
    long glGetUniformivARB;
    long glGetShaderSourceARB;
    long glNamedStringARB;
    long glDeleteNamedStringARB;
    long glCompileShaderIncludeARB;
    long glIsNamedStringARB;
    long glGetNamedStringARB;
    long glGetNamedStringivARB;
    long glBufferPageCommitmentARB;
    long glTexPageCommitmentARB;
    long glTexturePageCommitmentEXT;
    long glTexBufferARB;
    long glTextureBufferRangeEXT;
    long glCompressedTexImage1DARB;
    long glCompressedTexImage2DARB;
    long glCompressedTexImage3DARB;
    long glCompressedTexSubImage1DARB;
    long glCompressedTexSubImage2DARB;
    long glCompressedTexSubImage3DARB;
    long glGetCompressedTexImageARB;
    long glTextureStorage1DEXT;
    long glTextureStorage2DEXT;
    long glTextureStorage3DEXT;
    long glTextureStorage2DMultisampleEXT;
    long glTextureStorage3DMultisampleEXT;
    long glLoadTransposeMatrixfARB;
    long glMultTransposeMatrixfARB;
    long glVertexArrayVertexAttribLOffsetEXT;
    long glWeightbvARB;
    long glWeightsvARB;
    long glWeightivARB;
    long glWeightfvARB;
    long glWeightdvARB;
    long glWeightubvARB;
    long glWeightusvARB;
    long glWeightuivARB;
    long glWeightPointerARB;
    long glVertexBlendARB;
    long glVertexAttrib1sARB;
    long glVertexAttrib1fARB;
    long glVertexAttrib1dARB;
    long glVertexAttrib2sARB;
    long glVertexAttrib2fARB;
    long glVertexAttrib2dARB;
    long glVertexAttrib3sARB;
    long glVertexAttrib3fARB;
    long glVertexAttrib3dARB;
    long glVertexAttrib4sARB;
    long glVertexAttrib4fARB;
    long glVertexAttrib4dARB;
    long glVertexAttrib4NubARB;
    long glVertexAttribPointerARB;
    long glEnableVertexAttribArrayARB;
    long glDisableVertexAttribArrayARB;
    long glBindAttribLocationARB;
    long glGetActiveAttribARB;
    long glGetAttribLocationARB;
    long glGetVertexAttribfvARB;
    long glGetVertexAttribdvARB;
    long glGetVertexAttribivARB;
    long glGetVertexAttribPointervARB;
    long glWindowPos2fARB;
    long glWindowPos2dARB;
    long glWindowPos2iARB;
    long glWindowPos2sARB;
    long glWindowPos3fARB;
    long glWindowPos3dARB;
    long glWindowPos3iARB;
    long glWindowPos3sARB;
    long glDrawBuffersATI;
    long glElementPointerATI;
    long glDrawElementArrayATI;
    long glDrawRangeElementArrayATI;
    long glTexBumpParameterfvATI;
    long glTexBumpParameterivATI;
    long glGetTexBumpParameterfvATI;
    long glGetTexBumpParameterivATI;
    long glGenFragmentShadersATI;
    long glBindFragmentShaderATI;
    long glDeleteFragmentShaderATI;
    long glBeginFragmentShaderATI;
    long glEndFragmentShaderATI;
    long glPassTexCoordATI;
    long glSampleMapATI;
    long glColorFragmentOp1ATI;
    long glColorFragmentOp2ATI;
    long glColorFragmentOp3ATI;
    long glAlphaFragmentOp1ATI;
    long glAlphaFragmentOp2ATI;
    long glAlphaFragmentOp3ATI;
    long glSetFragmentShaderConstantATI;
    long glMapObjectBufferATI;
    long glUnmapObjectBufferATI;
    long glPNTrianglesfATI;
    long glPNTrianglesiATI;
    long glStencilOpSeparateATI;
    long glStencilFuncSeparateATI;
    long glNewObjectBufferATI;
    long glIsObjectBufferATI;
    long glUpdateObjectBufferATI;
    long glGetObjectBufferfvATI;
    long glGetObjectBufferivATI;
    long glFreeObjectBufferATI;
    long glArrayObjectATI;
    long glGetArrayObjectfvATI;
    long glGetArrayObjectivATI;
    long glVariantArrayObjectATI;
    long glGetVariantArrayObjectfvATI;
    long glGetVariantArrayObjectivATI;
    long glVertexAttribArrayObjectATI;
    long glGetVertexAttribArrayObjectfvATI;
    long glGetVertexAttribArrayObjectivATI;
    long glVertexStream2fATI;
    long glVertexStream2dATI;
    long glVertexStream2iATI;
    long glVertexStream2sATI;
    long glVertexStream3fATI;
    long glVertexStream3dATI;
    long glVertexStream3iATI;
    long glVertexStream3sATI;
    long glVertexStream4fATI;
    long glVertexStream4dATI;
    long glVertexStream4iATI;
    long glVertexStream4sATI;
    long glNormalStream3bATI;
    long glNormalStream3fATI;
    long glNormalStream3dATI;
    long glNormalStream3iATI;
    long glNormalStream3sATI;
    long glClientActiveVertexStreamATI;
    long glVertexBlendEnvfATI;
    long glVertexBlendEnviATI;
    long glUniformBufferEXT;
    long glGetUniformBufferSizeEXT;
    long glGetUniformOffsetEXT;
    long glBlendColorEXT;
    long glBlendEquationSeparateEXT;
    long glBlendFuncSeparateEXT;
    long glBlendEquationEXT;
    long glLockArraysEXT;
    long glUnlockArraysEXT;
    long glDepthBoundsEXT;
    long glClientAttribDefaultEXT;
    long glPushClientAttribDefaultEXT;
    long glMatrixLoadfEXT;
    long glMatrixLoaddEXT;
    long glMatrixMultfEXT;
    long glMatrixMultdEXT;
    long glMatrixLoadIdentityEXT;
    long glMatrixRotatefEXT;
    long glMatrixRotatedEXT;
    long glMatrixScalefEXT;
    long glMatrixScaledEXT;
    long glMatrixTranslatefEXT;
    long glMatrixTranslatedEXT;
    long glMatrixOrthoEXT;
    long glMatrixFrustumEXT;
    long glMatrixPushEXT;
    long glMatrixPopEXT;
    long glTextureParameteriEXT;
    long glTextureParameterivEXT;
    long glTextureParameterfEXT;
    long glTextureParameterfvEXT;
    long glTextureImage1DEXT;
    long glTextureImage2DEXT;
    long glTextureSubImage1DEXT;
    long glTextureSubImage2DEXT;
    long glCopyTextureImage1DEXT;
    long glCopyTextureImage2DEXT;
    long glCopyTextureSubImage1DEXT;
    long glCopyTextureSubImage2DEXT;
    long glGetTextureImageEXT;
    long glGetTextureParameterfvEXT;
    long glGetTextureParameterivEXT;
    long glGetTextureLevelParameterfvEXT;
    long glGetTextureLevelParameterivEXT;
    long glTextureImage3DEXT;
    long glTextureSubImage3DEXT;
    long glCopyTextureSubImage3DEXT;
    long glBindMultiTextureEXT;
    long glMultiTexCoordPointerEXT;
    long glMultiTexEnvfEXT;
    long glMultiTexEnvfvEXT;
    long glMultiTexEnviEXT;
    long glMultiTexEnvivEXT;
    long glMultiTexGendEXT;
    long glMultiTexGendvEXT;
    long glMultiTexGenfEXT;
    long glMultiTexGenfvEXT;
    long glMultiTexGeniEXT;
    long glMultiTexGenivEXT;
    long glGetMultiTexEnvfvEXT;
    long glGetMultiTexEnvivEXT;
    long glGetMultiTexGendvEXT;
    long glGetMultiTexGenfvEXT;
    long glGetMultiTexGenivEXT;
    long glMultiTexParameteriEXT;
    long glMultiTexParameterivEXT;
    long glMultiTexParameterfEXT;
    long glMultiTexParameterfvEXT;
    long glMultiTexImage1DEXT;
    long glMultiTexImage2DEXT;
    long glMultiTexSubImage1DEXT;
    long glMultiTexSubImage2DEXT;
    long glCopyMultiTexImage1DEXT;
    long glCopyMultiTexImage2DEXT;
    long glCopyMultiTexSubImage1DEXT;
    long glCopyMultiTexSubImage2DEXT;
    long glGetMultiTexImageEXT;
    long glGetMultiTexParameterfvEXT;
    long glGetMultiTexParameterivEXT;
    long glGetMultiTexLevelParameterfvEXT;
    long glGetMultiTexLevelParameterivEXT;
    long glMultiTexImage3DEXT;
    long glMultiTexSubImage3DEXT;
    long glCopyMultiTexSubImage3DEXT;
    long glEnableClientStateIndexedEXT;
    long glDisableClientStateIndexedEXT;
    long glEnableClientStateiEXT;
    long glDisableClientStateiEXT;
    long glGetFloatIndexedvEXT;
    long glGetDoubleIndexedvEXT;
    long glGetPointerIndexedvEXT;
    long glGetFloati_vEXT;
    long glGetDoublei_vEXT;
    long glGetPointeri_vEXT;
    long glNamedProgramStringEXT;
    long glNamedProgramLocalParameter4dEXT;
    long glNamedProgramLocalParameter4dvEXT;
    long glNamedProgramLocalParameter4fEXT;
    long glNamedProgramLocalParameter4fvEXT;
    long glGetNamedProgramLocalParameterdvEXT;
    long glGetNamedProgramLocalParameterfvEXT;
    long glGetNamedProgramivEXT;
    long glGetNamedProgramStringEXT;
    long glCompressedTextureImage3DEXT;
    long glCompressedTextureImage2DEXT;
    long glCompressedTextureImage1DEXT;
    long glCompressedTextureSubImage3DEXT;
    long glCompressedTextureSubImage2DEXT;
    long glCompressedTextureSubImage1DEXT;
    long glGetCompressedTextureImageEXT;
    long glCompressedMultiTexImage3DEXT;
    long glCompressedMultiTexImage2DEXT;
    long glCompressedMultiTexImage1DEXT;
    long glCompressedMultiTexSubImage3DEXT;
    long glCompressedMultiTexSubImage2DEXT;
    long glCompressedMultiTexSubImage1DEXT;
    long glGetCompressedMultiTexImageEXT;
    long glMatrixLoadTransposefEXT;
    long glMatrixLoadTransposedEXT;
    long glMatrixMultTransposefEXT;
    long glMatrixMultTransposedEXT;
    long glNamedBufferDataEXT;
    long glNamedBufferSubDataEXT;
    long glMapNamedBufferEXT;
    long glUnmapNamedBufferEXT;
    long glGetNamedBufferParameterivEXT;
    long glGetNamedBufferPointervEXT;
    long glGetNamedBufferSubDataEXT;
    long glProgramUniform1fEXT;
    long glProgramUniform2fEXT;
    long glProgramUniform3fEXT;
    long glProgramUniform4fEXT;
    long glProgramUniform1iEXT;
    long glProgramUniform2iEXT;
    long glProgramUniform3iEXT;
    long glProgramUniform4iEXT;
    long glProgramUniform1fvEXT;
    long glProgramUniform2fvEXT;
    long glProgramUniform3fvEXT;
    long glProgramUniform4fvEXT;
    long glProgramUniform1ivEXT;
    long glProgramUniform2ivEXT;
    long glProgramUniform3ivEXT;
    long glProgramUniform4ivEXT;
    long glProgramUniformMatrix2fvEXT;
    long glProgramUniformMatrix3fvEXT;
    long glProgramUniformMatrix4fvEXT;
    long glProgramUniformMatrix2x3fvEXT;
    long glProgramUniformMatrix3x2fvEXT;
    long glProgramUniformMatrix2x4fvEXT;
    long glProgramUniformMatrix4x2fvEXT;
    long glProgramUniformMatrix3x4fvEXT;
    long glProgramUniformMatrix4x3fvEXT;
    long glTextureBufferEXT;
    long glMultiTexBufferEXT;
    long glTextureParameterIivEXT;
    long glTextureParameterIuivEXT;
    long glGetTextureParameterIivEXT;
    long glGetTextureParameterIuivEXT;
    long glMultiTexParameterIivEXT;
    long glMultiTexParameterIuivEXT;
    long glGetMultiTexParameterIivEXT;
    long glGetMultiTexParameterIuivEXT;
    long glProgramUniform1uiEXT;
    long glProgramUniform2uiEXT;
    long glProgramUniform3uiEXT;
    long glProgramUniform4uiEXT;
    long glProgramUniform1uivEXT;
    long glProgramUniform2uivEXT;
    long glProgramUniform3uivEXT;
    long glProgramUniform4uivEXT;
    long glNamedProgramLocalParameters4fvEXT;
    long glNamedProgramLocalParameterI4iEXT;
    long glNamedProgramLocalParameterI4ivEXT;
    long glNamedProgramLocalParametersI4ivEXT;
    long glNamedProgramLocalParameterI4uiEXT;
    long glNamedProgramLocalParameterI4uivEXT;
    long glNamedProgramLocalParametersI4uivEXT;
    long glGetNamedProgramLocalParameterIivEXT;
    long glGetNamedProgramLocalParameterIuivEXT;
    long glNamedRenderbufferStorageEXT;
    long glGetNamedRenderbufferParameterivEXT;
    long glNamedRenderbufferStorageMultisampleEXT;
    long glNamedRenderbufferStorageMultisampleCoverageEXT;
    long glCheckNamedFramebufferStatusEXT;
    long glNamedFramebufferTexture1DEXT;
    long glNamedFramebufferTexture2DEXT;
    long glNamedFramebufferTexture3DEXT;
    long glNamedFramebufferRenderbufferEXT;
    long glGetNamedFramebufferAttachmentParameterivEXT;
    long glGenerateTextureMipmapEXT;
    long glGenerateMultiTexMipmapEXT;
    long glFramebufferDrawBufferEXT;
    long glFramebufferDrawBuffersEXT;
    long glFramebufferReadBufferEXT;
    long glGetFramebufferParameterivEXT;
    long glNamedCopyBufferSubDataEXT;
    long glNamedFramebufferTextureEXT;
    long glNamedFramebufferTextureLayerEXT;
    long glNamedFramebufferTextureFaceEXT;
    long glTextureRenderbufferEXT;
    long glMultiTexRenderbufferEXT;
    long glVertexArrayVertexOffsetEXT;
    long glVertexArrayColorOffsetEXT;
    long glVertexArrayEdgeFlagOffsetEXT;
    long glVertexArrayIndexOffsetEXT;
    long glVertexArrayNormalOffsetEXT;
    long glVertexArrayTexCoordOffsetEXT;
    long glVertexArrayMultiTexCoordOffsetEXT;
    long glVertexArrayFogCoordOffsetEXT;
    long glVertexArraySecondaryColorOffsetEXT;
    long glVertexArrayVertexAttribOffsetEXT;
    long glVertexArrayVertexAttribIOffsetEXT;
    long glEnableVertexArrayEXT;
    long glDisableVertexArrayEXT;
    long glEnableVertexArrayAttribEXT;
    long glDisableVertexArrayAttribEXT;
    long glGetVertexArrayIntegervEXT;
    long glGetVertexArrayPointervEXT;
    long glGetVertexArrayIntegeri_vEXT;
    long glGetVertexArrayPointeri_vEXT;
    long glMapNamedBufferRangeEXT;
    long glFlushMappedNamedBufferRangeEXT;
    long glColorMaskIndexedEXT;
    long glGetBooleanIndexedvEXT;
    long glGetIntegerIndexedvEXT;
    long glEnableIndexedEXT;
    long glDisableIndexedEXT;
    long glIsEnabledIndexedEXT;
    long glDrawArraysInstancedEXT;
    long glDrawElementsInstancedEXT;
    long glDrawRangeElementsEXT;
    long glFogCoordfEXT;
    long glFogCoorddEXT;
    long glFogCoordPointerEXT;
    long glBlitFramebufferEXT;
    long glRenderbufferStorageMultisampleEXT;
    long glIsRenderbufferEXT;
    long glBindRenderbufferEXT;
    long glDeleteRenderbuffersEXT;
    long glGenRenderbuffersEXT;
    long glRenderbufferStorageEXT;
    long glGetRenderbufferParameterivEXT;
    long glIsFramebufferEXT;
    long glBindFramebufferEXT;
    long glDeleteFramebuffersEXT;
    long glGenFramebuffersEXT;
    long glCheckFramebufferStatusEXT;
    long glFramebufferTexture1DEXT;
    long glFramebufferTexture2DEXT;
    long glFramebufferTexture3DEXT;
    long glFramebufferRenderbufferEXT;
    long glGetFramebufferAttachmentParameterivEXT;
    long glGenerateMipmapEXT;
    long glProgramParameteriEXT;
    long glFramebufferTextureEXT;
    long glFramebufferTextureLayerEXT;
    long glFramebufferTextureFaceEXT;
    long glProgramEnvParameters4fvEXT;
    long glProgramLocalParameters4fvEXT;
    long glVertexAttribI1iEXT;
    long glVertexAttribI2iEXT;
    long glVertexAttribI3iEXT;
    long glVertexAttribI4iEXT;
    long glVertexAttribI1uiEXT;
    long glVertexAttribI2uiEXT;
    long glVertexAttribI3uiEXT;
    long glVertexAttribI4uiEXT;
    long glVertexAttribI1ivEXT;
    long glVertexAttribI2ivEXT;
    long glVertexAttribI3ivEXT;
    long glVertexAttribI4ivEXT;
    long glVertexAttribI1uivEXT;
    long glVertexAttribI2uivEXT;
    long glVertexAttribI3uivEXT;
    long glVertexAttribI4uivEXT;
    long glVertexAttribI4bvEXT;
    long glVertexAttribI4svEXT;
    long glVertexAttribI4ubvEXT;
    long glVertexAttribI4usvEXT;
    long glVertexAttribIPointerEXT;
    long glGetVertexAttribIivEXT;
    long glGetVertexAttribIuivEXT;
    long glUniform1uiEXT;
    long glUniform2uiEXT;
    long glUniform3uiEXT;
    long glUniform4uiEXT;
    long glUniform1uivEXT;
    long glUniform2uivEXT;
    long glUniform3uivEXT;
    long glUniform4uivEXT;
    long glGetUniformuivEXT;
    long glBindFragDataLocationEXT;
    long glGetFragDataLocationEXT;
    long glMultiDrawArraysEXT;
    long glColorTableEXT;
    long glColorSubTableEXT;
    long glGetColorTableEXT;
    long glGetColorTableParameterivEXT;
    long glGetColorTableParameterfvEXT;
    long glPointParameterfEXT;
    long glPointParameterfvEXT;
    long glProvokingVertexEXT;
    long glSecondaryColor3bEXT;
    long glSecondaryColor3fEXT;
    long glSecondaryColor3dEXT;
    long glSecondaryColor3ubEXT;
    long glSecondaryColorPointerEXT;
    long glUseShaderProgramEXT;
    long glActiveProgramEXT;
    long glCreateShaderProgramEXT;
    long glBindImageTextureEXT;
    long glMemoryBarrierEXT;
    long glStencilClearTagEXT;
    long glActiveStencilFaceEXT;
    long glTexBufferEXT;
    long glClearColorIiEXT;
    long glClearColorIuiEXT;
    long glTexParameterIivEXT;
    long glTexParameterIuivEXT;
    long glGetTexParameterIivEXT;
    long glGetTexParameterIuivEXT;
    long glGetQueryObjecti64vEXT;
    long glGetQueryObjectui64vEXT;
    long glBindBufferRangeEXT;
    long glBindBufferOffsetEXT;
    long glBindBufferBaseEXT;
    long glBeginTransformFeedbackEXT;
    long glEndTransformFeedbackEXT;
    long glTransformFeedbackVaryingsEXT;
    long glGetTransformFeedbackVaryingEXT;
    long glVertexAttribL1dEXT;
    long glVertexAttribL2dEXT;
    long glVertexAttribL3dEXT;
    long glVertexAttribL4dEXT;
    long glVertexAttribL1dvEXT;
    long glVertexAttribL2dvEXT;
    long glVertexAttribL3dvEXT;
    long glVertexAttribL4dvEXT;
    long glVertexAttribLPointerEXT;
    long glGetVertexAttribLdvEXT;
    long glBeginVertexShaderEXT;
    long glEndVertexShaderEXT;
    long glBindVertexShaderEXT;
    long glGenVertexShadersEXT;
    long glDeleteVertexShaderEXT;
    long glShaderOp1EXT;
    long glShaderOp2EXT;
    long glShaderOp3EXT;
    long glSwizzleEXT;
    long glWriteMaskEXT;
    long glInsertComponentEXT;
    long glExtractComponentEXT;
    long glGenSymbolsEXT;
    long glSetInvariantEXT;
    long glSetLocalConstantEXT;
    long glVariantbvEXT;
    long glVariantsvEXT;
    long glVariantivEXT;
    long glVariantfvEXT;
    long glVariantdvEXT;
    long glVariantubvEXT;
    long glVariantusvEXT;
    long glVariantuivEXT;
    long glVariantPointerEXT;
    long glEnableVariantClientStateEXT;
    long glDisableVariantClientStateEXT;
    long glBindLightParameterEXT;
    long glBindMaterialParameterEXT;
    long glBindTexGenParameterEXT;
    long glBindTextureUnitParameterEXT;
    long glBindParameterEXT;
    long glIsVariantEnabledEXT;
    long glGetVariantBooleanvEXT;
    long glGetVariantIntegervEXT;
    long glGetVariantFloatvEXT;
    long glGetVariantPointervEXT;
    long glGetInvariantBooleanvEXT;
    long glGetInvariantIntegervEXT;
    long glGetInvariantFloatvEXT;
    long glGetLocalConstantBooleanvEXT;
    long glGetLocalConstantIntegervEXT;
    long glGetLocalConstantFloatvEXT;
    long glVertexWeightfEXT;
    long glVertexWeightPointerEXT;
    long glAccum;
    long glAlphaFunc;
    long glClearColor;
    long glClearAccum;
    long glClear;
    long glCallLists;
    long glCallList;
    long glBlendFunc;
    long glBitmap;
    long glBindTexture;
    long glPrioritizeTextures;
    long glAreTexturesResident;
    long glBegin;
    long glEnd;
    long glArrayElement;
    long glClearDepth;
    long glDeleteLists;
    long glDeleteTextures;
    long glCullFace;
    long glCopyTexSubImage2D;
    long glCopyTexSubImage1D;
    long glCopyTexImage2D;
    long glCopyTexImage1D;
    long glCopyPixels;
    long glColorPointer;
    long glColorMaterial;
    long glColorMask;
    long glColor3b;
    long glColor3f;
    long glColor3d;
    long glColor3ub;
    long glColor4b;
    long glColor4f;
    long glColor4d;
    long glColor4ub;
    long glClipPlane;
    long glClearStencil;
    long glEvalPoint1;
    long glEvalPoint2;
    long glEvalMesh1;
    long glEvalMesh2;
    long glEvalCoord1f;
    long glEvalCoord1d;
    long glEvalCoord2f;
    long glEvalCoord2d;
    long glEnableClientState;
    long glDisableClientState;
    long glEnable;
    long glDisable;
    long glEdgeFlagPointer;
    long glEdgeFlag;
    long glDrawPixels;
    long glDrawElements;
    long glDrawBuffer;
    long glDrawArrays;
    long glDepthRange;
    long glDepthMask;
    long glDepthFunc;
    long glFeedbackBuffer;
    long glGetPixelMapfv;
    long glGetPixelMapuiv;
    long glGetPixelMapusv;
    long glGetMaterialfv;
    long glGetMaterialiv;
    long glGetMapfv;
    long glGetMapdv;
    long glGetMapiv;
    long glGetLightfv;
    long glGetLightiv;
    long glGetError;
    long glGetClipPlane;
    long glGetBooleanv;
    long glGetDoublev;
    long glGetFloatv;
    long glGetIntegerv;
    long glGenTextures;
    long glGenLists;
    long glFrustum;
    long glFrontFace;
    long glFogf;
    long glFogi;
    long glFogfv;
    long glFogiv;
    long glFlush;
    long glFinish;
    long glGetPointerv;
    long glIsEnabled;
    long glInterleavedArrays;
    long glInitNames;
    long glHint;
    long glGetTexParameterfv;
    long glGetTexParameteriv;
    long glGetTexLevelParameterfv;
    long glGetTexLevelParameteriv;
    long glGetTexImage;
    long glGetTexGeniv;
    long glGetTexGenfv;
    long glGetTexGendv;
    long glGetTexEnviv;
    long glGetTexEnvfv;
    long glGetString;
    long glGetPolygonStipple;
    long glIsList;
    long glMaterialf;
    long glMateriali;
    long glMaterialfv;
    long glMaterialiv;
    long glMapGrid1f;
    long glMapGrid1d;
    long glMapGrid2f;
    long glMapGrid2d;
    long glMap2f;
    long glMap2d;
    long glMap1f;
    long glMap1d;
    long glLogicOp;
    long glLoadName;
    long glLoadMatrixf;
    long glLoadMatrixd;
    long glLoadIdentity;
    long glListBase;
    long glLineWidth;
    long glLineStipple;
    long glLightModelf;
    long glLightModeli;
    long glLightModelfv;
    long glLightModeliv;
    long glLightf;
    long glLighti;
    long glLightfv;
    long glLightiv;
    long glIsTexture;
    long glMatrixMode;
    long glPolygonStipple;
    long glPolygonOffset;
    long glPolygonMode;
    long glPointSize;
    long glPixelZoom;
    long glPixelTransferf;
    long glPixelTransferi;
    long glPixelStoref;
    long glPixelStorei;
    long glPixelMapfv;
    long glPixelMapuiv;
    long glPixelMapusv;
    long glPassThrough;
    long glOrtho;
    long glNormalPointer;
    long glNormal3b;
    long glNormal3f;
    long glNormal3d;
    long glNormal3i;
    long glNewList;
    long glEndList;
    long glMultMatrixf;
    long glMultMatrixd;
    long glShadeModel;
    long glSelectBuffer;
    long glScissor;
    long glScalef;
    long glScaled;
    long glRotatef;
    long glRotated;
    long glRenderMode;
    long glRectf;
    long glRectd;
    long glRecti;
    long glReadPixels;
    long glReadBuffer;
    long glRasterPos2f;
    long glRasterPos2d;
    long glRasterPos2i;
    long glRasterPos3f;
    long glRasterPos3d;
    long glRasterPos3i;
    long glRasterPos4f;
    long glRasterPos4d;
    long glRasterPos4i;
    long glPushName;
    long glPopName;
    long glPushMatrix;
    long glPopMatrix;
    long glPushClientAttrib;
    long glPopClientAttrib;
    long glPushAttrib;
    long glPopAttrib;
    long glStencilFunc;
    long glVertexPointer;
    long glVertex2f;
    long glVertex2d;
    long glVertex2i;
    long glVertex3f;
    long glVertex3d;
    long glVertex3i;
    long glVertex4f;
    long glVertex4d;
    long glVertex4i;
    long glTranslatef;
    long glTranslated;
    long glTexImage1D;
    long glTexImage2D;
    long glTexSubImage1D;
    long glTexSubImage2D;
    long glTexParameterf;
    long glTexParameteri;
    long glTexParameterfv;
    long glTexParameteriv;
    long glTexGenf;
    long glTexGend;
    long glTexGenfv;
    long glTexGendv;
    long glTexGeni;
    long glTexGeniv;
    long glTexEnvf;
    long glTexEnvi;
    long glTexEnvfv;
    long glTexEnviv;
    long glTexCoordPointer;
    long glTexCoord1f;
    long glTexCoord1d;
    long glTexCoord2f;
    long glTexCoord2d;
    long glTexCoord3f;
    long glTexCoord3d;
    long glTexCoord4f;
    long glTexCoord4d;
    long glStencilOp;
    long glStencilMask;
    long glViewport;
    long glDrawRangeElements;
    long glTexImage3D;
    long glTexSubImage3D;
    long glCopyTexSubImage3D;
    long glActiveTexture;
    long glClientActiveTexture;
    long glCompressedTexImage1D;
    long glCompressedTexImage2D;
    long glCompressedTexImage3D;
    long glCompressedTexSubImage1D;
    long glCompressedTexSubImage2D;
    long glCompressedTexSubImage3D;
    long glGetCompressedTexImage;
    long glMultiTexCoord1f;
    long glMultiTexCoord1d;
    long glMultiTexCoord2f;
    long glMultiTexCoord2d;
    long glMultiTexCoord3f;
    long glMultiTexCoord3d;
    long glMultiTexCoord4f;
    long glMultiTexCoord4d;
    long glLoadTransposeMatrixf;
    long glLoadTransposeMatrixd;
    long glMultTransposeMatrixf;
    long glMultTransposeMatrixd;
    long glSampleCoverage;
    long glBlendEquation;
    long glBlendColor;
    long glFogCoordf;
    long glFogCoordd;
    long glFogCoordPointer;
    long glMultiDrawArrays;
    long glPointParameteri;
    long glPointParameterf;
    long glPointParameteriv;
    long glPointParameterfv;
    long glSecondaryColor3b;
    long glSecondaryColor3f;
    long glSecondaryColor3d;
    long glSecondaryColor3ub;
    long glSecondaryColorPointer;
    long glBlendFuncSeparate;
    long glWindowPos2f;
    long glWindowPos2d;
    long glWindowPos2i;
    long glWindowPos3f;
    long glWindowPos3d;
    long glWindowPos3i;
    long glBindBuffer;
    long glDeleteBuffers;
    long glGenBuffers;
    long glIsBuffer;
    long glBufferData;
    long glBufferSubData;
    long glGetBufferSubData;
    long glMapBuffer;
    long glUnmapBuffer;
    long glGetBufferParameteriv;
    long glGetBufferPointerv;
    long glGenQueries;
    long glDeleteQueries;
    long glIsQuery;
    long glBeginQuery;
    long glEndQuery;
    long glGetQueryiv;
    long glGetQueryObjectiv;
    long glGetQueryObjectuiv;
    long glShaderSource;
    long glCreateShader;
    long glIsShader;
    long glCompileShader;
    long glDeleteShader;
    long glCreateProgram;
    long glIsProgram;
    long glAttachShader;
    long glDetachShader;
    long glLinkProgram;
    long glUseProgram;
    long glValidateProgram;
    long glDeleteProgram;
    long glUniform1f;
    long glUniform2f;
    long glUniform3f;
    long glUniform4f;
    long glUniform1i;
    long glUniform2i;
    long glUniform3i;
    long glUniform4i;
    long glUniform1fv;
    long glUniform2fv;
    long glUniform3fv;
    long glUniform4fv;
    long glUniform1iv;
    long glUniform2iv;
    long glUniform3iv;
    long glUniform4iv;
    long glUniformMatrix2fv;
    long glUniformMatrix3fv;
    long glUniformMatrix4fv;
    long glGetShaderiv;
    long glGetProgramiv;
    long glGetShaderInfoLog;
    long glGetProgramInfoLog;
    long glGetAttachedShaders;
    long glGetUniformLocation;
    long glGetActiveUniform;
    long glGetUniformfv;
    long glGetUniformiv;
    long glGetShaderSource;
    long glVertexAttrib1s;
    long glVertexAttrib1f;
    long glVertexAttrib1d;
    long glVertexAttrib2s;
    long glVertexAttrib2f;
    long glVertexAttrib2d;
    long glVertexAttrib3s;
    long glVertexAttrib3f;
    long glVertexAttrib3d;
    long glVertexAttrib4s;
    long glVertexAttrib4f;
    long glVertexAttrib4d;
    long glVertexAttrib4Nub;
    long glVertexAttribPointer;
    long glEnableVertexAttribArray;
    long glDisableVertexAttribArray;
    long glGetVertexAttribfv;
    long glGetVertexAttribdv;
    long glGetVertexAttribiv;
    long glGetVertexAttribPointerv;
    long glBindAttribLocation;
    long glGetActiveAttrib;
    long glGetAttribLocation;
    long glDrawBuffers;
    long glStencilOpSeparate;
    long glStencilFuncSeparate;
    long glStencilMaskSeparate;
    long glBlendEquationSeparate;
    long glUniformMatrix2x3fv;
    long glUniformMatrix3x2fv;
    long glUniformMatrix2x4fv;
    long glUniformMatrix4x2fv;
    long glUniformMatrix3x4fv;
    long glUniformMatrix4x3fv;
    long glGetStringi;
    long glClearBufferfv;
    long glClearBufferiv;
    long glClearBufferuiv;
    long glClearBufferfi;
    long glVertexAttribI1i;
    long glVertexAttribI2i;
    long glVertexAttribI3i;
    long glVertexAttribI4i;
    long glVertexAttribI1ui;
    long glVertexAttribI2ui;
    long glVertexAttribI3ui;
    long glVertexAttribI4ui;
    long glVertexAttribI1iv;
    long glVertexAttribI2iv;
    long glVertexAttribI3iv;
    long glVertexAttribI4iv;
    long glVertexAttribI1uiv;
    long glVertexAttribI2uiv;
    long glVertexAttribI3uiv;
    long glVertexAttribI4uiv;
    long glVertexAttribI4bv;
    long glVertexAttribI4sv;
    long glVertexAttribI4ubv;
    long glVertexAttribI4usv;
    long glVertexAttribIPointer;
    long glGetVertexAttribIiv;
    long glGetVertexAttribIuiv;
    long glUniform1ui;
    long glUniform2ui;
    long glUniform3ui;
    long glUniform4ui;
    long glUniform1uiv;
    long glUniform2uiv;
    long glUniform3uiv;
    long glUniform4uiv;
    long glGetUniformuiv;
    long glBindFragDataLocation;
    long glGetFragDataLocation;
    long glBeginConditionalRender;
    long glEndConditionalRender;
    long glMapBufferRange;
    long glFlushMappedBufferRange;
    long glClampColor;
    long glIsRenderbuffer;
    long glBindRenderbuffer;
    long glDeleteRenderbuffers;
    long glGenRenderbuffers;
    long glRenderbufferStorage;
    long glGetRenderbufferParameteriv;
    long glIsFramebuffer;
    long glBindFramebuffer;
    long glDeleteFramebuffers;
    long glGenFramebuffers;
    long glCheckFramebufferStatus;
    long glFramebufferTexture1D;
    long glFramebufferTexture2D;
    long glFramebufferTexture3D;
    long glFramebufferRenderbuffer;
    long glGetFramebufferAttachmentParameteriv;
    long glGenerateMipmap;
    long glRenderbufferStorageMultisample;
    long glBlitFramebuffer;
    long glTexParameterIiv;
    long glTexParameterIuiv;
    long glGetTexParameterIiv;
    long glGetTexParameterIuiv;
    long glFramebufferTextureLayer;
    long glColorMaski;
    long glGetBooleani_v;
    long glGetIntegeri_v;
    long glEnablei;
    long glDisablei;
    long glIsEnabledi;
    long glBindBufferRange;
    long glBindBufferBase;
    long glBeginTransformFeedback;
    long glEndTransformFeedback;
    long glTransformFeedbackVaryings;
    long glGetTransformFeedbackVarying;
    long glBindVertexArray;
    long glDeleteVertexArrays;
    long glGenVertexArrays;
    long glIsVertexArray;
    long glDrawArraysInstanced;
    long glDrawElementsInstanced;
    long glCopyBufferSubData;
    long glPrimitiveRestartIndex;
    long glTexBuffer;
    long glGetUniformIndices;
    long glGetActiveUniformsiv;
    long glGetActiveUniformName;
    long glGetUniformBlockIndex;
    long glGetActiveUniformBlockiv;
    long glGetActiveUniformBlockName;
    long glUniformBlockBinding;
    long glGetBufferParameteri64v;
    long glDrawElementsBaseVertex;
    long glDrawRangeElementsBaseVertex;
    long glDrawElementsInstancedBaseVertex;
    long glProvokingVertex;
    long glTexImage2DMultisample;
    long glTexImage3DMultisample;
    long glGetMultisamplefv;
    long glSampleMaski;
    long glFramebufferTexture;
    long glFenceSync;
    long glIsSync;
    long glDeleteSync;
    long glClientWaitSync;
    long glWaitSync;
    long glGetInteger64v;
    long glGetInteger64i_v;
    long glGetSynciv;
    long glBindFragDataLocationIndexed;
    long glGetFragDataIndex;
    long glGenSamplers;
    long glDeleteSamplers;
    long glIsSampler;
    long glBindSampler;
    long glSamplerParameteri;
    long glSamplerParameterf;
    long glSamplerParameteriv;
    long glSamplerParameterfv;
    long glSamplerParameterIiv;
    long glSamplerParameterIuiv;
    long glGetSamplerParameteriv;
    long glGetSamplerParameterfv;
    long glGetSamplerParameterIiv;
    long glGetSamplerParameterIuiv;
    long glQueryCounter;
    long glGetQueryObjecti64v;
    long glGetQueryObjectui64v;
    long glVertexAttribDivisor;
    long glVertexP2ui;
    long glVertexP3ui;
    long glVertexP4ui;
    long glVertexP2uiv;
    long glVertexP3uiv;
    long glVertexP4uiv;
    long glTexCoordP1ui;
    long glTexCoordP2ui;
    long glTexCoordP3ui;
    long glTexCoordP4ui;
    long glTexCoordP1uiv;
    long glTexCoordP2uiv;
    long glTexCoordP3uiv;
    long glTexCoordP4uiv;
    long glMultiTexCoordP1ui;
    long glMultiTexCoordP2ui;
    long glMultiTexCoordP3ui;
    long glMultiTexCoordP4ui;
    long glMultiTexCoordP1uiv;
    long glMultiTexCoordP2uiv;
    long glMultiTexCoordP3uiv;
    long glMultiTexCoordP4uiv;
    long glNormalP3ui;
    long glNormalP3uiv;
    long glColorP3ui;
    long glColorP4ui;
    long glColorP3uiv;
    long glColorP4uiv;
    long glSecondaryColorP3ui;
    long glSecondaryColorP3uiv;
    long glVertexAttribP1ui;
    long glVertexAttribP2ui;
    long glVertexAttribP3ui;
    long glVertexAttribP4ui;
    long glVertexAttribP1uiv;
    long glVertexAttribP2uiv;
    long glVertexAttribP3uiv;
    long glVertexAttribP4uiv;
    long glBlendEquationi;
    long glBlendEquationSeparatei;
    long glBlendFunci;
    long glBlendFuncSeparatei;
    long glDrawArraysIndirect;
    long glDrawElementsIndirect;
    long glUniform1d;
    long glUniform2d;
    long glUniform3d;
    long glUniform4d;
    long glUniform1dv;
    long glUniform2dv;
    long glUniform3dv;
    long glUniform4dv;
    long glUniformMatrix2dv;
    long glUniformMatrix3dv;
    long glUniformMatrix4dv;
    long glUniformMatrix2x3dv;
    long glUniformMatrix2x4dv;
    long glUniformMatrix3x2dv;
    long glUniformMatrix3x4dv;
    long glUniformMatrix4x2dv;
    long glUniformMatrix4x3dv;
    long glGetUniformdv;
    long glMinSampleShading;
    long glGetSubroutineUniformLocation;
    long glGetSubroutineIndex;
    long glGetActiveSubroutineUniformiv;
    long glGetActiveSubroutineUniformName;
    long glGetActiveSubroutineName;
    long glUniformSubroutinesuiv;
    long glGetUniformSubroutineuiv;
    long glGetProgramStageiv;
    long glPatchParameteri;
    long glPatchParameterfv;
    long glBindTransformFeedback;
    long glDeleteTransformFeedbacks;
    long glGenTransformFeedbacks;
    long glIsTransformFeedback;
    long glPauseTransformFeedback;
    long glResumeTransformFeedback;
    long glDrawTransformFeedback;
    long glDrawTransformFeedbackStream;
    long glBeginQueryIndexed;
    long glEndQueryIndexed;
    long glGetQueryIndexediv;
    long glReleaseShaderCompiler;
    long glShaderBinary;
    long glGetShaderPrecisionFormat;
    long glDepthRangef;
    long glClearDepthf;
    long glGetProgramBinary;
    long glProgramBinary;
    long glProgramParameteri;
    long glUseProgramStages;
    long glActiveShaderProgram;
    long glCreateShaderProgramv;
    long glBindProgramPipeline;
    long glDeleteProgramPipelines;
    long glGenProgramPipelines;
    long glIsProgramPipeline;
    long glGetProgramPipelineiv;
    long glProgramUniform1i;
    long glProgramUniform2i;
    long glProgramUniform3i;
    long glProgramUniform4i;
    long glProgramUniform1f;
    long glProgramUniform2f;
    long glProgramUniform3f;
    long glProgramUniform4f;
    long glProgramUniform1d;
    long glProgramUniform2d;
    long glProgramUniform3d;
    long glProgramUniform4d;
    long glProgramUniform1iv;
    long glProgramUniform2iv;
    long glProgramUniform3iv;
    long glProgramUniform4iv;
    long glProgramUniform1fv;
    long glProgramUniform2fv;
    long glProgramUniform3fv;
    long glProgramUniform4fv;
    long glProgramUniform1dv;
    long glProgramUniform2dv;
    long glProgramUniform3dv;
    long glProgramUniform4dv;
    long glProgramUniform1ui;
    long glProgramUniform2ui;
    long glProgramUniform3ui;
    long glProgramUniform4ui;
    long glProgramUniform1uiv;
    long glProgramUniform2uiv;
    long glProgramUniform3uiv;
    long glProgramUniform4uiv;
    long glProgramUniformMatrix2fv;
    long glProgramUniformMatrix3fv;
    long glProgramUniformMatrix4fv;
    long glProgramUniformMatrix2dv;
    long glProgramUniformMatrix3dv;
    long glProgramUniformMatrix4dv;
    long glProgramUniformMatrix2x3fv;
    long glProgramUniformMatrix3x2fv;
    long glProgramUniformMatrix2x4fv;
    long glProgramUniformMatrix4x2fv;
    long glProgramUniformMatrix3x4fv;
    long glProgramUniformMatrix4x3fv;
    long glProgramUniformMatrix2x3dv;
    long glProgramUniformMatrix3x2dv;
    long glProgramUniformMatrix2x4dv;
    long glProgramUniformMatrix4x2dv;
    long glProgramUniformMatrix3x4dv;
    long glProgramUniformMatrix4x3dv;
    long glValidateProgramPipeline;
    long glGetProgramPipelineInfoLog;
    long glVertexAttribL1d;
    long glVertexAttribL2d;
    long glVertexAttribL3d;
    long glVertexAttribL4d;
    long glVertexAttribL1dv;
    long glVertexAttribL2dv;
    long glVertexAttribL3dv;
    long glVertexAttribL4dv;
    long glVertexAttribLPointer;
    long glGetVertexAttribLdv;
    long glViewportArrayv;
    long glViewportIndexedf;
    long glViewportIndexedfv;
    long glScissorArrayv;
    long glScissorIndexed;
    long glScissorIndexedv;
    long glDepthRangeArrayv;
    long glDepthRangeIndexed;
    long glGetFloati_v;
    long glGetDoublei_v;
    long glGetActiveAtomicCounterBufferiv;
    long glTexStorage1D;
    long glTexStorage2D;
    long glTexStorage3D;
    long glDrawTransformFeedbackInstanced;
    long glDrawTransformFeedbackStreamInstanced;
    long glDrawArraysInstancedBaseInstance;
    long glDrawElementsInstancedBaseInstance;
    long glDrawElementsInstancedBaseVertexBaseInstance;
    long glBindImageTexture;
    long glMemoryBarrier;
    long glGetInternalformativ;
    long glClearBufferData;
    long glClearBufferSubData;
    long glDispatchCompute;
    long glDispatchComputeIndirect;
    long glCopyImageSubData;
    long glDebugMessageControl;
    long glDebugMessageInsert;
    long glDebugMessageCallback;
    long glGetDebugMessageLog;
    long glPushDebugGroup;
    long glPopDebugGroup;
    long glObjectLabel;
    long glGetObjectLabel;
    long glObjectPtrLabel;
    long glGetObjectPtrLabel;
    long glFramebufferParameteri;
    long glGetFramebufferParameteriv;
    long glGetInternalformati64v;
    long glInvalidateTexSubImage;
    long glInvalidateTexImage;
    long glInvalidateBufferSubData;
    long glInvalidateBufferData;
    long glInvalidateFramebuffer;
    long glInvalidateSubFramebuffer;
    long glMultiDrawArraysIndirect;
    long glMultiDrawElementsIndirect;
    long glGetProgramInterfaceiv;
    long glGetProgramResourceIndex;
    long glGetProgramResourceName;
    long glGetProgramResourceiv;
    long glGetProgramResourceLocation;
    long glGetProgramResourceLocationIndex;
    long glShaderStorageBlockBinding;
    long glTexBufferRange;
    long glTexStorage2DMultisample;
    long glTexStorage3DMultisample;
    long glTextureView;
    long glBindVertexBuffer;
    long glVertexAttribFormat;
    long glVertexAttribIFormat;
    long glVertexAttribLFormat;
    long glVertexAttribBinding;
    long glVertexBindingDivisor;
    long glBufferStorage;
    long glClearTexImage;
    long glClearTexSubImage;
    long glBindBuffersBase;
    long glBindBuffersRange;
    long glBindTextures;
    long glBindSamplers;
    long glBindImageTextures;
    long glBindVertexBuffers;
    long glClipControl;
    long glCreateTransformFeedbacks;
    long glTransformFeedbackBufferBase;
    long glTransformFeedbackBufferRange;
    long glGetTransformFeedbackiv;
    long glGetTransformFeedbacki_v;
    long glGetTransformFeedbacki64_v;
    long glCreateBuffers;
    long glNamedBufferStorage;
    long glNamedBufferData;
    long glNamedBufferSubData;
    long glCopyNamedBufferSubData;
    long glClearNamedBufferData;
    long glClearNamedBufferSubData;
    long glMapNamedBuffer;
    long glMapNamedBufferRange;
    long glUnmapNamedBuffer;
    long glFlushMappedNamedBufferRange;
    long glGetNamedBufferParameteriv;
    long glGetNamedBufferParameteri64v;
    long glGetNamedBufferPointerv;
    long glGetNamedBufferSubData;
    long glCreateFramebuffers;
    long glNamedFramebufferRenderbuffer;
    long glNamedFramebufferParameteri;
    long glNamedFramebufferTexture;
    long glNamedFramebufferTextureLayer;
    long glNamedFramebufferDrawBuffer;
    long glNamedFramebufferDrawBuffers;
    long glNamedFramebufferReadBuffer;
    long glInvalidateNamedFramebufferData;
    long glInvalidateNamedFramebufferSubData;
    long glClearNamedFramebufferiv;
    long glClearNamedFramebufferuiv;
    long glClearNamedFramebufferfv;
    long glClearNamedFramebufferfi;
    long glBlitNamedFramebuffer;
    long glCheckNamedFramebufferStatus;
    long glGetNamedFramebufferParameteriv;
    long glGetNamedFramebufferAttachmentParameteriv;
    long glCreateRenderbuffers;
    long glNamedRenderbufferStorage;
    long glNamedRenderbufferStorageMultisample;
    long glGetNamedRenderbufferParameteriv;
    long glCreateTextures;
    long glTextureBuffer;
    long glTextureBufferRange;
    long glTextureStorage1D;
    long glTextureStorage2D;
    long glTextureStorage3D;
    long glTextureStorage2DMultisample;
    long glTextureStorage3DMultisample;
    long glTextureSubImage1D;
    long glTextureSubImage2D;
    long glTextureSubImage3D;
    long glCompressedTextureSubImage1D;
    long glCompressedTextureSubImage2D;
    long glCompressedTextureSubImage3D;
    long glCopyTextureSubImage1D;
    long glCopyTextureSubImage2D;
    long glCopyTextureSubImage3D;
    long glTextureParameterf;
    long glTextureParameterfv;
    long glTextureParameteri;
    long glTextureParameterIiv;
    long glTextureParameterIuiv;
    long glTextureParameteriv;
    long glGenerateTextureMipmap;
    long glBindTextureUnit;
    long glGetTextureImage;
    long glGetCompressedTextureImage;
    long glGetTextureLevelParameterfv;
    long glGetTextureLevelParameteriv;
    long glGetTextureParameterfv;
    long glGetTextureParameterIiv;
    long glGetTextureParameterIuiv;
    long glGetTextureParameteriv;
    long glCreateVertexArrays;
    long glDisableVertexArrayAttrib;
    long glEnableVertexArrayAttrib;
    long glVertexArrayElementBuffer;
    long glVertexArrayVertexBuffer;
    long glVertexArrayVertexBuffers;
    long glVertexArrayAttribFormat;
    long glVertexArrayAttribIFormat;
    long glVertexArrayAttribLFormat;
    long glVertexArrayAttribBinding;
    long glVertexArrayBindingDivisor;
    long glGetVertexArrayiv;
    long glGetVertexArrayIndexediv;
    long glGetVertexArrayIndexed64iv;
    long glCreateSamplers;
    long glCreateProgramPipelines;
    long glCreateQueries;
    long glMemoryBarrierByRegion;
    long glGetTextureSubImage;
    long glGetCompressedTextureSubImage;
    long glTextureBarrier;
    long glGetGraphicsResetStatus;
    long glReadnPixels;
    long glGetnUniformfv;
    long glGetnUniformiv;
    long glGetnUniformuiv;
    long glFrameTerminatorGREMEDY;
    long glStringMarkerGREMEDY;
    long glMapTexture2DINTEL;
    long glUnmapTexture2DINTEL;
    long glSyncTextureINTEL;
    long glMultiDrawArraysIndirectBindlessNV;
    long glMultiDrawElementsIndirectBindlessNV;
    long glGetTextureHandleNV;
    long glGetTextureSamplerHandleNV;
    long glMakeTextureHandleResidentNV;
    long glMakeTextureHandleNonResidentNV;
    long glGetImageHandleNV;
    long glMakeImageHandleResidentNV;
    long glMakeImageHandleNonResidentNV;
    long glUniformHandleui64NV;
    long glUniformHandleui64vNV;
    long glProgramUniformHandleui64NV;
    long glProgramUniformHandleui64vNV;
    long glIsTextureHandleResidentNV;
    long glIsImageHandleResidentNV;
    long glBlendParameteriNV;
    long glBlendBarrierNV;
    long glBeginConditionalRenderNV;
    long glEndConditionalRenderNV;
    long glCopyImageSubDataNV;
    long glDepthRangedNV;
    long glClearDepthdNV;
    long glDepthBoundsdNV;
    long glDrawTextureNV;
    long glGetMapControlPointsNV;
    long glMapControlPointsNV;
    long glMapParameterfvNV;
    long glMapParameterivNV;
    long glGetMapParameterfvNV;
    long glGetMapParameterivNV;
    long glGetMapAttribParameterfvNV;
    long glGetMapAttribParameterivNV;
    long glEvalMapsNV;
    long glGetMultisamplefvNV;
    long glSampleMaskIndexedNV;
    long glTexRenderbufferNV;
    long glGenFencesNV;
    long glDeleteFencesNV;
    long glSetFenceNV;
    long glTestFenceNV;
    long glFinishFenceNV;
    long glIsFenceNV;
    long glGetFenceivNV;
    long glProgramNamedParameter4fNV;
    long glProgramNamedParameter4dNV;
    long glGetProgramNamedParameterfvNV;
    long glGetProgramNamedParameterdvNV;
    long glRenderbufferStorageMultisampleCoverageNV;
    long glProgramVertexLimitNV;
    long glProgramLocalParameterI4iNV;
    long glProgramLocalParameterI4ivNV;
    long glProgramLocalParametersI4ivNV;
    long glProgramLocalParameterI4uiNV;
    long glProgramLocalParameterI4uivNV;
    long glProgramLocalParametersI4uivNV;
    long glProgramEnvParameterI4iNV;
    long glProgramEnvParameterI4ivNV;
    long glProgramEnvParametersI4ivNV;
    long glProgramEnvParameterI4uiNV;
    long glProgramEnvParameterI4uivNV;
    long glProgramEnvParametersI4uivNV;
    long glGetProgramLocalParameterIivNV;
    long glGetProgramLocalParameterIuivNV;
    long glGetProgramEnvParameterIivNV;
    long glGetProgramEnvParameterIuivNV;
    long glUniform1i64NV;
    long glUniform2i64NV;
    long glUniform3i64NV;
    long glUniform4i64NV;
    long glUniform1i64vNV;
    long glUniform2i64vNV;
    long glUniform3i64vNV;
    long glUniform4i64vNV;
    long glUniform1ui64NV;
    long glUniform2ui64NV;
    long glUniform3ui64NV;
    long glUniform4ui64NV;
    long glUniform1ui64vNV;
    long glUniform2ui64vNV;
    long glUniform3ui64vNV;
    long glUniform4ui64vNV;
    long glGetUniformi64vNV;
    long glGetUniformui64vNV;
    long glProgramUniform1i64NV;
    long glProgramUniform2i64NV;
    long glProgramUniform3i64NV;
    long glProgramUniform4i64NV;
    long glProgramUniform1i64vNV;
    long glProgramUniform2i64vNV;
    long glProgramUniform3i64vNV;
    long glProgramUniform4i64vNV;
    long glProgramUniform1ui64NV;
    long glProgramUniform2ui64NV;
    long glProgramUniform3ui64NV;
    long glProgramUniform4ui64NV;
    long glProgramUniform1ui64vNV;
    long glProgramUniform2ui64vNV;
    long glProgramUniform3ui64vNV;
    long glProgramUniform4ui64vNV;
    long glVertex2hNV;
    long glVertex3hNV;
    long glVertex4hNV;
    long glNormal3hNV;
    long glColor3hNV;
    long glColor4hNV;
    long glTexCoord1hNV;
    long glTexCoord2hNV;
    long glTexCoord3hNV;
    long glTexCoord4hNV;
    long glMultiTexCoord1hNV;
    long glMultiTexCoord2hNV;
    long glMultiTexCoord3hNV;
    long glMultiTexCoord4hNV;
    long glFogCoordhNV;
    long glSecondaryColor3hNV;
    long glVertexWeighthNV;
    long glVertexAttrib1hNV;
    long glVertexAttrib2hNV;
    long glVertexAttrib3hNV;
    long glVertexAttrib4hNV;
    long glVertexAttribs1hvNV;
    long glVertexAttribs2hvNV;
    long glVertexAttribs3hvNV;
    long glVertexAttribs4hvNV;
    long glGenOcclusionQueriesNV;
    long glDeleteOcclusionQueriesNV;
    long glIsOcclusionQueryNV;
    long glBeginOcclusionQueryNV;
    long glEndOcclusionQueryNV;
    long glGetOcclusionQueryuivNV;
    long glGetOcclusionQueryivNV;
    long glProgramBufferParametersfvNV;
    long glProgramBufferParametersIivNV;
    long glProgramBufferParametersIuivNV;
    long glPathCommandsNV;
    long glPathCoordsNV;
    long glPathSubCommandsNV;
    long glPathSubCoordsNV;
    long glPathStringNV;
    long glPathGlyphsNV;
    long glPathGlyphRangeNV;
    long glWeightPathsNV;
    long glCopyPathNV;
    long glInterpolatePathsNV;
    long glTransformPathNV;
    long glPathParameterivNV;
    long glPathParameteriNV;
    long glPathParameterfvNV;
    long glPathParameterfNV;
    long glPathDashArrayNV;
    long glGenPathsNV;
    long glDeletePathsNV;
    long glIsPathNV;
    long glPathStencilFuncNV;
    long glPathStencilDepthOffsetNV;
    long glStencilFillPathNV;
    long glStencilStrokePathNV;
    long glStencilFillPathInstancedNV;
    long glStencilStrokePathInstancedNV;
    long glPathCoverDepthFuncNV;
    long glPathColorGenNV;
    long glPathTexGenNV;
    long glPathFogGenNV;
    long glCoverFillPathNV;
    long glCoverStrokePathNV;
    long glCoverFillPathInstancedNV;
    long glCoverStrokePathInstancedNV;
    long glGetPathParameterivNV;
    long glGetPathParameterfvNV;
    long glGetPathCommandsNV;
    long glGetPathCoordsNV;
    long glGetPathDashArrayNV;
    long glGetPathMetricsNV;
    long glGetPathMetricRangeNV;
    long glGetPathSpacingNV;
    long glGetPathColorGenivNV;
    long glGetPathColorGenfvNV;
    long glGetPathTexGenivNV;
    long glGetPathTexGenfvNV;
    long glIsPointInFillPathNV;
    long glIsPointInStrokePathNV;
    long glGetPathLengthNV;
    long glPointAlongPathNV;
    long glPixelDataRangeNV;
    long glFlushPixelDataRangeNV;
    long glPointParameteriNV;
    long glPointParameterivNV;
    long glPresentFrameKeyedNV;
    long glPresentFrameDualFillNV;
    long glGetVideoivNV;
    long glGetVideouivNV;
    long glGetVideoi64vNV;
    long glGetVideoui64vNV;
    long glPrimitiveRestartNV;
    long glPrimitiveRestartIndexNV;
    long glLoadProgramNV;
    long glBindProgramNV;
    long glDeleteProgramsNV;
    long glGenProgramsNV;
    long glGetProgramivNV;
    long glGetProgramStringNV;
    long glIsProgramNV;
    long glAreProgramsResidentNV;
    long glRequestResidentProgramsNV;
    long glCombinerParameterfNV;
    long glCombinerParameterfvNV;
    long glCombinerParameteriNV;
    long glCombinerParameterivNV;
    long glCombinerInputNV;
    long glCombinerOutputNV;
    long glFinalCombinerInputNV;
    long glGetCombinerInputParameterfvNV;
    long glGetCombinerInputParameterivNV;
    long glGetCombinerOutputParameterfvNV;
    long glGetCombinerOutputParameterivNV;
    long glGetFinalCombinerInputParameterfvNV;
    long glGetFinalCombinerInputParameterivNV;
    long glCombinerStageParameterfvNV;
    long glGetCombinerStageParameterfvNV;
    long glMakeBufferResidentNV;
    long glMakeBufferNonResidentNV;
    long glIsBufferResidentNV;
    long glMakeNamedBufferResidentNV;
    long glMakeNamedBufferNonResidentNV;
    long glIsNamedBufferResidentNV;
    long glGetBufferParameterui64vNV;
    long glGetNamedBufferParameterui64vNV;
    long glGetIntegerui64vNV;
    long glUniformui64NV;
    long glUniformui64vNV;
    long glProgramUniformui64NV;
    long glProgramUniformui64vNV;
    long glTextureBarrierNV;
    long glTexImage2DMultisampleCoverageNV;
    long glTexImage3DMultisampleCoverageNV;
    long glTextureImage2DMultisampleNV;
    long glTextureImage3DMultisampleNV;
    long glTextureImage2DMultisampleCoverageNV;
    long glTextureImage3DMultisampleCoverageNV;
    long glBindBufferRangeNV;
    long glBindBufferOffsetNV;
    long glBindBufferBaseNV;
    long glTransformFeedbackAttribsNV;
    long glTransformFeedbackVaryingsNV;
    long glBeginTransformFeedbackNV;
    long glEndTransformFeedbackNV;
    long glGetVaryingLocationNV;
    long glGetActiveVaryingNV;
    long glActiveVaryingNV;
    long glGetTransformFeedbackVaryingNV;
    long glBindTransformFeedbackNV;
    long glDeleteTransformFeedbacksNV;
    long glGenTransformFeedbacksNV;
    long glIsTransformFeedbackNV;
    long glPauseTransformFeedbackNV;
    long glResumeTransformFeedbackNV;
    long glDrawTransformFeedbackNV;
    long glVertexArrayRangeNV;
    long glFlushVertexArrayRangeNV;
    long glAllocateMemoryNV;
    long glFreeMemoryNV;
    long glVertexAttribL1i64NV;
    long glVertexAttribL2i64NV;
    long glVertexAttribL3i64NV;
    long glVertexAttribL4i64NV;
    long glVertexAttribL1i64vNV;
    long glVertexAttribL2i64vNV;
    long glVertexAttribL3i64vNV;
    long glVertexAttribL4i64vNV;
    long glVertexAttribL1ui64NV;
    long glVertexAttribL2ui64NV;
    long glVertexAttribL3ui64NV;
    long glVertexAttribL4ui64NV;
    long glVertexAttribL1ui64vNV;
    long glVertexAttribL2ui64vNV;
    long glVertexAttribL3ui64vNV;
    long glVertexAttribL4ui64vNV;
    long glGetVertexAttribLi64vNV;
    long glGetVertexAttribLui64vNV;
    long glVertexAttribLFormatNV;
    long glBufferAddressRangeNV;
    long glVertexFormatNV;
    long glNormalFormatNV;
    long glColorFormatNV;
    long glIndexFormatNV;
    long glTexCoordFormatNV;
    long glEdgeFlagFormatNV;
    long glSecondaryColorFormatNV;
    long glFogCoordFormatNV;
    long glVertexAttribFormatNV;
    long glVertexAttribIFormatNV;
    long glGetIntegerui64i_vNV;
    long glExecuteProgramNV;
    long glGetProgramParameterfvNV;
    long glGetProgramParameterdvNV;
    long glGetTrackMatrixivNV;
    long glGetVertexAttribfvNV;
    long glGetVertexAttribdvNV;
    long glGetVertexAttribivNV;
    long glGetVertexAttribPointervNV;
    long glProgramParameter4fNV;
    long glProgramParameter4dNV;
    long glProgramParameters4fvNV;
    long glProgramParameters4dvNV;
    long glTrackMatrixNV;
    long glVertexAttribPointerNV;
    long glVertexAttrib1sNV;
    long glVertexAttrib1fNV;
    long glVertexAttrib1dNV;
    long glVertexAttrib2sNV;
    long glVertexAttrib2fNV;
    long glVertexAttrib2dNV;
    long glVertexAttrib3sNV;
    long glVertexAttrib3fNV;
    long glVertexAttrib3dNV;
    long glVertexAttrib4sNV;
    long glVertexAttrib4fNV;
    long glVertexAttrib4dNV;
    long glVertexAttrib4ubNV;
    long glVertexAttribs1svNV;
    long glVertexAttribs1fvNV;
    long glVertexAttribs1dvNV;
    long glVertexAttribs2svNV;
    long glVertexAttribs2fvNV;
    long glVertexAttribs2dvNV;
    long glVertexAttribs3svNV;
    long glVertexAttribs3fvNV;
    long glVertexAttribs3dvNV;
    long glVertexAttribs4svNV;
    long glVertexAttribs4fvNV;
    long glVertexAttribs4dvNV;
    long glBeginVideoCaptureNV;
    long glBindVideoCaptureStreamBufferNV;
    long glBindVideoCaptureStreamTextureNV;
    long glEndVideoCaptureNV;
    long glGetVideoCaptureivNV;
    long glGetVideoCaptureStreamivNV;
    long glGetVideoCaptureStreamfvNV;
    long glGetVideoCaptureStreamdvNV;
    long glVideoCaptureNV;
    long glVideoCaptureStreamParameterivNV;
    long glVideoCaptureStreamParameterfvNV;
    long glVideoCaptureStreamParameterdvNV;
    
    private boolean AMD_debug_output_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress(new String[] { "glDebugMessageEnableAMD", "glDebugMessageEnableAMDX" });
        this.glDebugMessageEnableAMD = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress(new String[] { "glDebugMessageInsertAMD", "glDebugMessageInsertAMDX" });
        this.glDebugMessageInsertAMD = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress(new String[] { "glDebugMessageCallbackAMD", "glDebugMessageCallbackAMDX" });
        this.glDebugMessageCallbackAMD = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress(new String[] { "glGetDebugMessageLogAMD", "glGetDebugMessageLogAMDX" });
        this.glGetDebugMessageLogAMD = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean AMD_draw_buffers_blend_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBlendFuncIndexedAMD");
        this.glBlendFuncIndexedAMD = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBlendFuncSeparateIndexedAMD");
        this.glBlendFuncSeparateIndexedAMD = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glBlendEquationIndexedAMD");
        this.glBlendEquationIndexedAMD = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBlendEquationSeparateIndexedAMD");
        this.glBlendEquationSeparateIndexedAMD = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean AMD_interleaved_elements_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexAttribParameteriAMD");
        this.glVertexAttribParameteriAMD = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean AMD_multi_draw_indirect_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMultiDrawArraysIndirectAMD");
        this.glMultiDrawArraysIndirectAMD = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMultiDrawElementsIndirectAMD");
        this.glMultiDrawElementsIndirectAMD = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean AMD_name_gen_delete_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGenNamesAMD");
        this.glGenNamesAMD = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteNamesAMD");
        this.glDeleteNamesAMD = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glIsNameAMD");
        this.glIsNameAMD = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean AMD_performance_monitor_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetPerfMonitorGroupsAMD");
        this.glGetPerfMonitorGroupsAMD = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetPerfMonitorCountersAMD");
        this.glGetPerfMonitorCountersAMD = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetPerfMonitorGroupStringAMD");
        this.glGetPerfMonitorGroupStringAMD = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetPerfMonitorCounterStringAMD");
        this.glGetPerfMonitorCounterStringAMD = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetPerfMonitorCounterInfoAMD");
        this.glGetPerfMonitorCounterInfoAMD = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGenPerfMonitorsAMD");
        this.glGenPerfMonitorsAMD = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glDeletePerfMonitorsAMD");
        this.glDeletePerfMonitorsAMD = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glSelectPerfMonitorCountersAMD");
        this.glSelectPerfMonitorCountersAMD = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glBeginPerfMonitorAMD");
        this.glBeginPerfMonitorAMD = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glEndPerfMonitorAMD");
        this.glEndPerfMonitorAMD = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGetPerfMonitorCounterDataAMD");
        this.glGetPerfMonitorCounterDataAMD = functionAddress11;
        return b10 & functionAddress11 != 0L;
    }
    
    private boolean AMD_sample_positions_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glSetMultisamplefvAMD");
        this.glSetMultisamplefvAMD = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean AMD_sparse_texture_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTexStorageSparseAMD");
        this.glTexStorageSparseAMD = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glTextureStorageSparseAMD");
        this.glTextureStorageSparseAMD = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean AMD_stencil_operation_extended_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glStencilOpValueAMD");
        this.glStencilOpValueAMD = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean AMD_vertex_shader_tessellator_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTessellationFactorAMD");
        this.glTessellationFactorAMD = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glTessellationModeAMD");
        this.glTessellationModeAMD = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean APPLE_element_array_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glElementPointerAPPLE");
        this.glElementPointerAPPLE = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawElementArrayAPPLE");
        this.glDrawElementArrayAPPLE = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDrawRangeElementArrayAPPLE");
        this.glDrawRangeElementArrayAPPLE = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glMultiDrawElementArrayAPPLE");
        this.glMultiDrawElementArrayAPPLE = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glMultiDrawRangeElementArrayAPPLE");
        this.glMultiDrawRangeElementArrayAPPLE = functionAddress5;
        return b4 & functionAddress5 != 0L;
    }
    
    private boolean APPLE_fence_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGenFencesAPPLE");
        this.glGenFencesAPPLE = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteFencesAPPLE");
        this.glDeleteFencesAPPLE = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glSetFenceAPPLE");
        this.glSetFenceAPPLE = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glIsFenceAPPLE");
        this.glIsFenceAPPLE = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glTestFenceAPPLE");
        this.glTestFenceAPPLE = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glFinishFenceAPPLE");
        this.glFinishFenceAPPLE = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glTestObjectAPPLE");
        this.glTestObjectAPPLE = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glFinishObjectAPPLE");
        this.glFinishObjectAPPLE = functionAddress8;
        return b7 & functionAddress8 != 0L;
    }
    
    private boolean APPLE_flush_buffer_range_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBufferParameteriAPPLE");
        this.glBufferParameteriAPPLE = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glFlushMappedBufferRangeAPPLE");
        this.glFlushMappedBufferRangeAPPLE = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean APPLE_object_purgeable_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glObjectPurgeableAPPLE");
        this.glObjectPurgeableAPPLE = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glObjectUnpurgeableAPPLE");
        this.glObjectUnpurgeableAPPLE = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetObjectParameterivAPPLE");
        this.glGetObjectParameterivAPPLE = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean APPLE_texture_range_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTextureRangeAPPLE");
        this.glTextureRangeAPPLE = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetTexParameterPointervAPPLE");
        this.glGetTexParameterPointervAPPLE = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean APPLE_vertex_array_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindVertexArrayAPPLE");
        this.glBindVertexArrayAPPLE = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteVertexArraysAPPLE");
        this.glDeleteVertexArraysAPPLE = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGenVertexArraysAPPLE");
        this.glGenVertexArraysAPPLE = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glIsVertexArrayAPPLE");
        this.glIsVertexArrayAPPLE = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean APPLE_vertex_array_range_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexArrayRangeAPPLE");
        this.glVertexArrayRangeAPPLE = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glFlushVertexArrayRangeAPPLE");
        this.glFlushVertexArrayRangeAPPLE = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexArrayParameteriAPPLE");
        this.glVertexArrayParameteriAPPLE = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean APPLE_vertex_program_evaluators_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glEnableVertexAttribAPPLE");
        this.glEnableVertexAttribAPPLE = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDisableVertexAttribAPPLE");
        this.glDisableVertexAttribAPPLE = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glIsVertexAttribEnabledAPPLE");
        this.glIsVertexAttribEnabledAPPLE = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glMapVertexAttrib1dAPPLE");
        this.glMapVertexAttrib1dAPPLE = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glMapVertexAttrib1fAPPLE");
        this.glMapVertexAttrib1fAPPLE = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glMapVertexAttrib2dAPPLE");
        this.glMapVertexAttrib2dAPPLE = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glMapVertexAttrib2fAPPLE");
        this.glMapVertexAttrib2fAPPLE = functionAddress7;
        return b6 & functionAddress7 != 0L;
    }
    
    private boolean ARB_ES2_compatibility_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glReleaseShaderCompiler");
        this.glReleaseShaderCompiler = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glShaderBinary");
        this.glShaderBinary = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetShaderPrecisionFormat");
        this.glGetShaderPrecisionFormat = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glDepthRangef");
        this.glDepthRangef = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glClearDepthf");
        this.glClearDepthf = functionAddress5;
        return b4 & functionAddress5 != 0L;
    }
    
    private boolean ARB_ES3_1_compatibility_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMemoryBarrierByRegion");
        this.glMemoryBarrierByRegion = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_base_instance_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawArraysInstancedBaseInstance");
        this.glDrawArraysInstancedBaseInstance = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawElementsInstancedBaseInstance");
        this.glDrawElementsInstancedBaseInstance = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDrawElementsInstancedBaseVertexBaseInstance");
        this.glDrawElementsInstancedBaseVertexBaseInstance = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean ARB_bindless_texture_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetTextureHandleARB");
        this.glGetTextureHandleARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetTextureSamplerHandleARB");
        this.glGetTextureSamplerHandleARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glMakeTextureHandleResidentARB");
        this.glMakeTextureHandleResidentARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glMakeTextureHandleNonResidentARB");
        this.glMakeTextureHandleNonResidentARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetImageHandleARB");
        this.glGetImageHandleARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glMakeImageHandleResidentARB");
        this.glMakeImageHandleResidentARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glMakeImageHandleNonResidentARB");
        this.glMakeImageHandleNonResidentARB = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glUniformHandleui64ARB");
        this.glUniformHandleui64ARB = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glUniformHandleui64vARB");
        this.glUniformHandleui64vARB = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glProgramUniformHandleui64ARB");
        this.glProgramUniformHandleui64ARB = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glProgramUniformHandleui64vARB");
        this.glProgramUniformHandleui64vARB = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glIsTextureHandleResidentARB");
        this.glIsTextureHandleResidentARB = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glIsImageHandleResidentARB");
        this.glIsImageHandleResidentARB = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glVertexAttribL1ui64ARB");
        this.glVertexAttribL1ui64ARB = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glVertexAttribL1ui64vARB");
        this.glVertexAttribL1ui64vARB = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glGetVertexAttribLui64vARB");
        this.glGetVertexAttribLui64vARB = functionAddress16;
        return b15 & functionAddress16 != 0L;
    }
    
    private boolean ARB_blend_func_extended_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindFragDataLocationIndexed");
        this.glBindFragDataLocationIndexed = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetFragDataIndex");
        this.glGetFragDataIndex = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_buffer_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindBufferARB");
        this.glBindBufferARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteBuffersARB");
        this.glDeleteBuffersARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGenBuffersARB");
        this.glGenBuffersARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glIsBufferARB");
        this.glIsBufferARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glBufferDataARB");
        this.glBufferDataARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glBufferSubDataARB");
        this.glBufferSubDataARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetBufferSubDataARB");
        this.glGetBufferSubDataARB = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glMapBufferARB");
        this.glMapBufferARB = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glUnmapBufferARB");
        this.glUnmapBufferARB = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glGetBufferParameterivARB");
        this.glGetBufferParameterivARB = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGetBufferPointervARB");
        this.glGetBufferPointervARB = functionAddress11;
        return b10 & functionAddress11 != 0L;
    }
    
    private boolean ARB_buffer_storage_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glBufferStorage");
        this.glBufferStorage = functionAddress;
        final boolean b = functionAddress != 0L;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress2 = GLContext.getFunctionAddress("glNamedBufferStorageEXT");
            this.glNamedBufferStorageEXT = functionAddress2;
            if (functionAddress2 == 0L) {
                final boolean b2 = false;
                return b & b2;
            }
        }
        final boolean b2 = true;
        return b & b2;
    }
    
    private boolean ARB_cl_event_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glCreateSyncFromCLeventARB");
        this.glCreateSyncFromCLeventARB = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_clear_buffer_object_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glClearBufferData");
        this.glClearBufferData = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glClearBufferSubData");
        this.glClearBufferSubData = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        boolean b3 = false;
        Label_0076: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress3 = GLContext.getFunctionAddress("glClearNamedBufferDataEXT");
                this.glClearNamedBufferDataEXT = functionAddress3;
                if (functionAddress3 == 0L) {
                    b3 = false;
                    break Label_0076;
                }
            }
            b3 = true;
        }
        final boolean b4 = b2 & b3;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress4 = GLContext.getFunctionAddress("glClearNamedBufferSubDataEXT");
            this.glClearNamedBufferSubDataEXT = functionAddress4;
            if (functionAddress4 == 0L) {
                final boolean b5 = false;
                return b4 & b5;
            }
        }
        final boolean b5 = true;
        return b4 & b5;
    }
    
    private boolean ARB_clear_texture_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glClearTexImage");
        this.glClearTexImage = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glClearTexSubImage");
        this.glClearTexSubImage = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_clip_control_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glClipControl");
        this.glClipControl = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_color_buffer_float_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glClampColorARB");
        this.glClampColorARB = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_compute_shader_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDispatchCompute");
        this.glDispatchCompute = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDispatchComputeIndirect");
        this.glDispatchComputeIndirect = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_compute_variable_group_size_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDispatchComputeGroupSizeARB");
        this.glDispatchComputeGroupSizeARB = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_copy_buffer_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glCopyBufferSubData");
        this.glCopyBufferSubData = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_copy_image_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glCopyImageSubData");
        this.glCopyImageSubData = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_debug_output_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDebugMessageControlARB");
        this.glDebugMessageControlARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDebugMessageInsertARB");
        this.glDebugMessageInsertARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDebugMessageCallbackARB");
        this.glDebugMessageCallbackARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetDebugMessageLogARB");
        this.glGetDebugMessageLogARB = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean ARB_direct_state_access_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glCreateTransformFeedbacks");
        this.glCreateTransformFeedbacks = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glTransformFeedbackBufferBase");
        this.glTransformFeedbackBufferBase = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glTransformFeedbackBufferRange");
        this.glTransformFeedbackBufferRange = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetTransformFeedbackiv");
        this.glGetTransformFeedbackiv = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetTransformFeedbacki_v");
        this.glGetTransformFeedbacki_v = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetTransformFeedbacki64_v");
        this.glGetTransformFeedbacki64_v = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glCreateBuffers");
        this.glCreateBuffers = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glNamedBufferStorage");
        this.glNamedBufferStorage = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glNamedBufferData");
        this.glNamedBufferData = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glNamedBufferSubData");
        this.glNamedBufferSubData = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glCopyNamedBufferSubData");
        this.glCopyNamedBufferSubData = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glClearNamedBufferData");
        this.glClearNamedBufferData = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glClearNamedBufferSubData");
        this.glClearNamedBufferSubData = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glMapNamedBuffer");
        this.glMapNamedBuffer = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glMapNamedBufferRange");
        this.glMapNamedBufferRange = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glUnmapNamedBuffer");
        this.glUnmapNamedBuffer = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glFlushMappedNamedBufferRange");
        this.glFlushMappedNamedBufferRange = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetNamedBufferParameteriv");
        this.glGetNamedBufferParameteriv = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glGetNamedBufferParameteri64v");
        this.glGetNamedBufferParameteri64v = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glGetNamedBufferPointerv");
        this.glGetNamedBufferPointerv = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glGetNamedBufferSubData");
        this.glGetNamedBufferSubData = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glCreateFramebuffers");
        this.glCreateFramebuffers = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glNamedFramebufferRenderbuffer");
        this.glNamedFramebufferRenderbuffer = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glNamedFramebufferParameteri");
        this.glNamedFramebufferParameteri = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glNamedFramebufferTexture");
        this.glNamedFramebufferTexture = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glNamedFramebufferTextureLayer");
        this.glNamedFramebufferTextureLayer = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glNamedFramebufferDrawBuffer");
        this.glNamedFramebufferDrawBuffer = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glNamedFramebufferDrawBuffers");
        this.glNamedFramebufferDrawBuffers = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glNamedFramebufferReadBuffer");
        this.glNamedFramebufferReadBuffer = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glInvalidateNamedFramebufferData");
        this.glInvalidateNamedFramebufferData = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glInvalidateNamedFramebufferSubData");
        this.glInvalidateNamedFramebufferSubData = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glClearNamedFramebufferiv");
        this.glClearNamedFramebufferiv = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glClearNamedFramebufferuiv");
        this.glClearNamedFramebufferuiv = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glClearNamedFramebufferfv");
        this.glClearNamedFramebufferfv = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glClearNamedFramebufferfi");
        this.glClearNamedFramebufferfi = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glBlitNamedFramebuffer");
        this.glBlitNamedFramebuffer = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glCheckNamedFramebufferStatus");
        this.glCheckNamedFramebufferStatus = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glGetNamedFramebufferParameteriv");
        this.glGetNamedFramebufferParameteriv = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glGetNamedFramebufferAttachmentParameteriv");
        this.glGetNamedFramebufferAttachmentParameteriv = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glCreateRenderbuffers");
        this.glCreateRenderbuffers = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glNamedRenderbufferStorage");
        this.glNamedRenderbufferStorage = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glNamedRenderbufferStorageMultisample");
        this.glNamedRenderbufferStorageMultisample = functionAddress42;
        final boolean b42 = b41 & functionAddress42 != 0L;
        final long functionAddress43 = GLContext.getFunctionAddress("glGetNamedRenderbufferParameteriv");
        this.glGetNamedRenderbufferParameteriv = functionAddress43;
        final boolean b43 = b42 & functionAddress43 != 0L;
        final long functionAddress44 = GLContext.getFunctionAddress("glCreateTextures");
        this.glCreateTextures = functionAddress44;
        final boolean b44 = b43 & functionAddress44 != 0L;
        final long functionAddress45 = GLContext.getFunctionAddress("glTextureBuffer");
        this.glTextureBuffer = functionAddress45;
        final boolean b45 = b44 & functionAddress45 != 0L;
        final long functionAddress46 = GLContext.getFunctionAddress("glTextureBufferRange");
        this.glTextureBufferRange = functionAddress46;
        final boolean b46 = b45 & functionAddress46 != 0L;
        final long functionAddress47 = GLContext.getFunctionAddress("glTextureStorage1D");
        this.glTextureStorage1D = functionAddress47;
        final boolean b47 = b46 & functionAddress47 != 0L;
        final long functionAddress48 = GLContext.getFunctionAddress("glTextureStorage2D");
        this.glTextureStorage2D = functionAddress48;
        final boolean b48 = b47 & functionAddress48 != 0L;
        final long functionAddress49 = GLContext.getFunctionAddress("glTextureStorage3D");
        this.glTextureStorage3D = functionAddress49;
        final boolean b49 = b48 & functionAddress49 != 0L;
        final long functionAddress50 = GLContext.getFunctionAddress("glTextureStorage2DMultisample");
        this.glTextureStorage2DMultisample = functionAddress50;
        final boolean b50 = b49 & functionAddress50 != 0L;
        final long functionAddress51 = GLContext.getFunctionAddress("glTextureStorage3DMultisample");
        this.glTextureStorage3DMultisample = functionAddress51;
        final boolean b51 = b50 & functionAddress51 != 0L;
        final long functionAddress52 = GLContext.getFunctionAddress("glTextureSubImage1D");
        this.glTextureSubImage1D = functionAddress52;
        final boolean b52 = b51 & functionAddress52 != 0L;
        final long functionAddress53 = GLContext.getFunctionAddress("glTextureSubImage2D");
        this.glTextureSubImage2D = functionAddress53;
        final boolean b53 = b52 & functionAddress53 != 0L;
        final long functionAddress54 = GLContext.getFunctionAddress("glTextureSubImage3D");
        this.glTextureSubImage3D = functionAddress54;
        final boolean b54 = b53 & functionAddress54 != 0L;
        final long functionAddress55 = GLContext.getFunctionAddress("glCompressedTextureSubImage1D");
        this.glCompressedTextureSubImage1D = functionAddress55;
        final boolean b55 = b54 & functionAddress55 != 0L;
        final long functionAddress56 = GLContext.getFunctionAddress("glCompressedTextureSubImage2D");
        this.glCompressedTextureSubImage2D = functionAddress56;
        final boolean b56 = b55 & functionAddress56 != 0L;
        final long functionAddress57 = GLContext.getFunctionAddress("glCompressedTextureSubImage3D");
        this.glCompressedTextureSubImage3D = functionAddress57;
        final boolean b57 = b56 & functionAddress57 != 0L;
        final long functionAddress58 = GLContext.getFunctionAddress("glCopyTextureSubImage1D");
        this.glCopyTextureSubImage1D = functionAddress58;
        final boolean b58 = b57 & functionAddress58 != 0L;
        final long functionAddress59 = GLContext.getFunctionAddress("glCopyTextureSubImage2D");
        this.glCopyTextureSubImage2D = functionAddress59;
        final boolean b59 = b58 & functionAddress59 != 0L;
        final long functionAddress60 = GLContext.getFunctionAddress("glCopyTextureSubImage3D");
        this.glCopyTextureSubImage3D = functionAddress60;
        final boolean b60 = b59 & functionAddress60 != 0L;
        final long functionAddress61 = GLContext.getFunctionAddress("glTextureParameterf");
        this.glTextureParameterf = functionAddress61;
        final boolean b61 = b60 & functionAddress61 != 0L;
        final long functionAddress62 = GLContext.getFunctionAddress("glTextureParameterfv");
        this.glTextureParameterfv = functionAddress62;
        final boolean b62 = b61 & functionAddress62 != 0L;
        final long functionAddress63 = GLContext.getFunctionAddress("glTextureParameteri");
        this.glTextureParameteri = functionAddress63;
        final boolean b63 = b62 & functionAddress63 != 0L;
        final long functionAddress64 = GLContext.getFunctionAddress("glTextureParameterIiv");
        this.glTextureParameterIiv = functionAddress64;
        final boolean b64 = b63 & functionAddress64 != 0L;
        final long functionAddress65 = GLContext.getFunctionAddress("glTextureParameterIuiv");
        this.glTextureParameterIuiv = functionAddress65;
        final boolean b65 = b64 & functionAddress65 != 0L;
        final long functionAddress66 = GLContext.getFunctionAddress("glTextureParameteriv");
        this.glTextureParameteriv = functionAddress66;
        final boolean b66 = b65 & functionAddress66 != 0L;
        final long functionAddress67 = GLContext.getFunctionAddress("glGenerateTextureMipmap");
        this.glGenerateTextureMipmap = functionAddress67;
        final boolean b67 = b66 & functionAddress67 != 0L;
        final long functionAddress68 = GLContext.getFunctionAddress("glBindTextureUnit");
        this.glBindTextureUnit = functionAddress68;
        final boolean b68 = b67 & functionAddress68 != 0L;
        final long functionAddress69 = GLContext.getFunctionAddress("glGetTextureImage");
        this.glGetTextureImage = functionAddress69;
        final boolean b69 = b68 & functionAddress69 != 0L;
        final long functionAddress70 = GLContext.getFunctionAddress("glGetCompressedTextureImage");
        this.glGetCompressedTextureImage = functionAddress70;
        final boolean b70 = b69 & functionAddress70 != 0L;
        final long functionAddress71 = GLContext.getFunctionAddress("glGetTextureLevelParameterfv");
        this.glGetTextureLevelParameterfv = functionAddress71;
        final boolean b71 = b70 & functionAddress71 != 0L;
        final long functionAddress72 = GLContext.getFunctionAddress("glGetTextureLevelParameteriv");
        this.glGetTextureLevelParameteriv = functionAddress72;
        final boolean b72 = b71 & functionAddress72 != 0L;
        final long functionAddress73 = GLContext.getFunctionAddress("glGetTextureParameterfv");
        this.glGetTextureParameterfv = functionAddress73;
        final boolean b73 = b72 & functionAddress73 != 0L;
        final long functionAddress74 = GLContext.getFunctionAddress("glGetTextureParameterIiv");
        this.glGetTextureParameterIiv = functionAddress74;
        final boolean b74 = b73 & functionAddress74 != 0L;
        final long functionAddress75 = GLContext.getFunctionAddress("glGetTextureParameterIuiv");
        this.glGetTextureParameterIuiv = functionAddress75;
        final boolean b75 = b74 & functionAddress75 != 0L;
        final long functionAddress76 = GLContext.getFunctionAddress("glGetTextureParameteriv");
        this.glGetTextureParameteriv = functionAddress76;
        final boolean b76 = b75 & functionAddress76 != 0L;
        final long functionAddress77 = GLContext.getFunctionAddress("glCreateVertexArrays");
        this.glCreateVertexArrays = functionAddress77;
        final boolean b77 = b76 & functionAddress77 != 0L;
        final long functionAddress78 = GLContext.getFunctionAddress("glDisableVertexArrayAttrib");
        this.glDisableVertexArrayAttrib = functionAddress78;
        final boolean b78 = b77 & functionAddress78 != 0L;
        final long functionAddress79 = GLContext.getFunctionAddress("glEnableVertexArrayAttrib");
        this.glEnableVertexArrayAttrib = functionAddress79;
        final boolean b79 = b78 & functionAddress79 != 0L;
        final long functionAddress80 = GLContext.getFunctionAddress("glVertexArrayElementBuffer");
        this.glVertexArrayElementBuffer = functionAddress80;
        final boolean b80 = b79 & functionAddress80 != 0L;
        final long functionAddress81 = GLContext.getFunctionAddress("glVertexArrayVertexBuffer");
        this.glVertexArrayVertexBuffer = functionAddress81;
        final boolean b81 = b80 & functionAddress81 != 0L;
        final long functionAddress82 = GLContext.getFunctionAddress("glVertexArrayVertexBuffers");
        this.glVertexArrayVertexBuffers = functionAddress82;
        final boolean b82 = b81 & functionAddress82 != 0L;
        final long functionAddress83 = GLContext.getFunctionAddress("glVertexArrayAttribFormat");
        this.glVertexArrayAttribFormat = functionAddress83;
        final boolean b83 = b82 & functionAddress83 != 0L;
        final long functionAddress84 = GLContext.getFunctionAddress("glVertexArrayAttribIFormat");
        this.glVertexArrayAttribIFormat = functionAddress84;
        final boolean b84 = b83 & functionAddress84 != 0L;
        final long functionAddress85 = GLContext.getFunctionAddress("glVertexArrayAttribLFormat");
        this.glVertexArrayAttribLFormat = functionAddress85;
        final boolean b85 = b84 & functionAddress85 != 0L;
        final long functionAddress86 = GLContext.getFunctionAddress("glVertexArrayAttribBinding");
        this.glVertexArrayAttribBinding = functionAddress86;
        final boolean b86 = b85 & functionAddress86 != 0L;
        final long functionAddress87 = GLContext.getFunctionAddress("glVertexArrayBindingDivisor");
        this.glVertexArrayBindingDivisor = functionAddress87;
        final boolean b87 = b86 & functionAddress87 != 0L;
        final long functionAddress88 = GLContext.getFunctionAddress("glGetVertexArrayiv");
        this.glGetVertexArrayiv = functionAddress88;
        final boolean b88 = b87 & functionAddress88 != 0L;
        final long functionAddress89 = GLContext.getFunctionAddress("glGetVertexArrayIndexediv");
        this.glGetVertexArrayIndexediv = functionAddress89;
        final boolean b89 = b88 & functionAddress89 != 0L;
        final long functionAddress90 = GLContext.getFunctionAddress("glGetVertexArrayIndexed64iv");
        this.glGetVertexArrayIndexed64iv = functionAddress90;
        final boolean b90 = b89 & functionAddress90 != 0L;
        final long functionAddress91 = GLContext.getFunctionAddress("glCreateSamplers");
        this.glCreateSamplers = functionAddress91;
        final boolean b91 = b90 & functionAddress91 != 0L;
        final long functionAddress92 = GLContext.getFunctionAddress("glCreateProgramPipelines");
        this.glCreateProgramPipelines = functionAddress92;
        final boolean b92 = b91 & functionAddress92 != 0L;
        final long functionAddress93 = GLContext.getFunctionAddress("glCreateQueries");
        this.glCreateQueries = functionAddress93;
        return b92 & functionAddress93 != 0L;
    }
    
    private boolean ARB_draw_buffers_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawBuffersARB");
        this.glDrawBuffersARB = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_draw_buffers_blend_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBlendEquationiARB");
        this.glBlendEquationiARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBlendEquationSeparateiARB");
        this.glBlendEquationSeparateiARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glBlendFunciARB");
        this.glBlendFunciARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBlendFuncSeparateiARB");
        this.glBlendFuncSeparateiARB = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean ARB_draw_elements_base_vertex_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawElementsBaseVertex");
        this.glDrawElementsBaseVertex = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawRangeElementsBaseVertex");
        this.glDrawRangeElementsBaseVertex = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDrawElementsInstancedBaseVertex");
        this.glDrawElementsInstancedBaseVertex = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean ARB_draw_indirect_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawArraysIndirect");
        this.glDrawArraysIndirect = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawElementsIndirect");
        this.glDrawElementsIndirect = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_draw_instanced_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawArraysInstancedARB");
        this.glDrawArraysInstancedARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawElementsInstancedARB");
        this.glDrawElementsInstancedARB = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_framebuffer_no_attachments_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glFramebufferParameteri");
        this.glFramebufferParameteri = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetFramebufferParameteriv");
        this.glGetFramebufferParameteriv = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        boolean b3 = false;
        Label_0076: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress3 = GLContext.getFunctionAddress("glNamedFramebufferParameteriEXT");
                this.glNamedFramebufferParameteriEXT = functionAddress3;
                if (functionAddress3 == 0L) {
                    b3 = false;
                    break Label_0076;
                }
            }
            b3 = true;
        }
        final boolean b4 = b2 & b3;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress4 = GLContext.getFunctionAddress("glGetNamedFramebufferParameterivEXT");
            this.glGetNamedFramebufferParameterivEXT = functionAddress4;
            if (functionAddress4 == 0L) {
                final boolean b5 = false;
                return b4 & b5;
            }
        }
        final boolean b5 = true;
        return b4 & b5;
    }
    
    private boolean ARB_framebuffer_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glIsRenderbuffer");
        this.glIsRenderbuffer = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBindRenderbuffer");
        this.glBindRenderbuffer = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDeleteRenderbuffers");
        this.glDeleteRenderbuffers = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGenRenderbuffers");
        this.glGenRenderbuffers = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glRenderbufferStorage");
        this.glRenderbufferStorage = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glRenderbufferStorageMultisample");
        this.glRenderbufferStorageMultisample = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetRenderbufferParameteriv");
        this.glGetRenderbufferParameteriv = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glIsFramebuffer");
        this.glIsFramebuffer = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glBindFramebuffer");
        this.glBindFramebuffer = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glDeleteFramebuffers");
        this.glDeleteFramebuffers = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGenFramebuffers");
        this.glGenFramebuffers = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glCheckFramebufferStatus");
        this.glCheckFramebufferStatus = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glFramebufferTexture1D");
        this.glFramebufferTexture1D = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glFramebufferTexture2D");
        this.glFramebufferTexture2D = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glFramebufferTexture3D");
        this.glFramebufferTexture3D = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glFramebufferTextureLayer");
        this.glFramebufferTextureLayer = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glFramebufferRenderbuffer");
        this.glFramebufferRenderbuffer = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetFramebufferAttachmentParameteriv");
        this.glGetFramebufferAttachmentParameteriv = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glBlitFramebuffer");
        this.glBlitFramebuffer = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glGenerateMipmap");
        this.glGenerateMipmap = functionAddress20;
        return b19 & functionAddress20 != 0L;
    }
    
    private boolean ARB_geometry_shader4_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProgramParameteriARB");
        this.glProgramParameteriARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glFramebufferTextureARB");
        this.glFramebufferTextureARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glFramebufferTextureLayerARB");
        this.glFramebufferTextureLayerARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glFramebufferTextureFaceARB");
        this.glFramebufferTextureFaceARB = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean ARB_get_program_binary_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetProgramBinary");
        this.glGetProgramBinary = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glProgramBinary");
        this.glProgramBinary = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glProgramParameteri");
        this.glProgramParameteri = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean ARB_get_texture_sub_image_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetTextureSubImage");
        this.glGetTextureSubImage = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetCompressedTextureSubImage");
        this.glGetCompressedTextureSubImage = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_gpu_shader_fp64_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glUniform1d");
        this.glUniform1d = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glUniform2d");
        this.glUniform2d = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glUniform3d");
        this.glUniform3d = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glUniform4d");
        this.glUniform4d = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glUniform1dv");
        this.glUniform1dv = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glUniform2dv");
        this.glUniform2dv = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glUniform3dv");
        this.glUniform3dv = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glUniform4dv");
        this.glUniform4dv = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glUniformMatrix2dv");
        this.glUniformMatrix2dv = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glUniformMatrix3dv");
        this.glUniformMatrix3dv = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glUniformMatrix4dv");
        this.glUniformMatrix4dv = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glUniformMatrix2x3dv");
        this.glUniformMatrix2x3dv = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glUniformMatrix2x4dv");
        this.glUniformMatrix2x4dv = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glUniformMatrix3x2dv");
        this.glUniformMatrix3x2dv = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glUniformMatrix3x4dv");
        this.glUniformMatrix3x4dv = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glUniformMatrix4x2dv");
        this.glUniformMatrix4x2dv = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glUniformMatrix4x3dv");
        this.glUniformMatrix4x3dv = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetUniformdv");
        this.glGetUniformdv = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        boolean b19 = false;
        Label_0428: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress19 = GLContext.getFunctionAddress("glProgramUniform1dEXT");
                this.glProgramUniform1dEXT = functionAddress19;
                if (functionAddress19 == 0L) {
                    b19 = false;
                    break Label_0428;
                }
            }
            b19 = true;
        }
        final boolean b20 = b18 & b19;
        boolean b21 = false;
        Label_0462: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress20 = GLContext.getFunctionAddress("glProgramUniform2dEXT");
                this.glProgramUniform2dEXT = functionAddress20;
                if (functionAddress20 == 0L) {
                    b21 = false;
                    break Label_0462;
                }
            }
            b21 = true;
        }
        final boolean b22 = b20 & b21;
        boolean b23 = false;
        Label_0496: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress21 = GLContext.getFunctionAddress("glProgramUniform3dEXT");
                this.glProgramUniform3dEXT = functionAddress21;
                if (functionAddress21 == 0L) {
                    b23 = false;
                    break Label_0496;
                }
            }
            b23 = true;
        }
        final boolean b24 = b22 & b23;
        boolean b25 = false;
        Label_0530: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress22 = GLContext.getFunctionAddress("glProgramUniform4dEXT");
                this.glProgramUniform4dEXT = functionAddress22;
                if (functionAddress22 == 0L) {
                    b25 = false;
                    break Label_0530;
                }
            }
            b25 = true;
        }
        final boolean b26 = b24 & b25;
        boolean b27 = false;
        Label_0564: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress23 = GLContext.getFunctionAddress("glProgramUniform1dvEXT");
                this.glProgramUniform1dvEXT = functionAddress23;
                if (functionAddress23 == 0L) {
                    b27 = false;
                    break Label_0564;
                }
            }
            b27 = true;
        }
        final boolean b28 = b26 & b27;
        boolean b29 = false;
        Label_0598: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress24 = GLContext.getFunctionAddress("glProgramUniform2dvEXT");
                this.glProgramUniform2dvEXT = functionAddress24;
                if (functionAddress24 == 0L) {
                    b29 = false;
                    break Label_0598;
                }
            }
            b29 = true;
        }
        final boolean b30 = b28 & b29;
        boolean b31 = false;
        Label_0632: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress25 = GLContext.getFunctionAddress("glProgramUniform3dvEXT");
                this.glProgramUniform3dvEXT = functionAddress25;
                if (functionAddress25 == 0L) {
                    b31 = false;
                    break Label_0632;
                }
            }
            b31 = true;
        }
        final boolean b32 = b30 & b31;
        boolean b33 = false;
        Label_0666: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress26 = GLContext.getFunctionAddress("glProgramUniform4dvEXT");
                this.glProgramUniform4dvEXT = functionAddress26;
                if (functionAddress26 == 0L) {
                    b33 = false;
                    break Label_0666;
                }
            }
            b33 = true;
        }
        final boolean b34 = b32 & b33;
        boolean b35 = false;
        Label_0700: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress27 = GLContext.getFunctionAddress("glProgramUniformMatrix2dvEXT");
                this.glProgramUniformMatrix2dvEXT = functionAddress27;
                if (functionAddress27 == 0L) {
                    b35 = false;
                    break Label_0700;
                }
            }
            b35 = true;
        }
        final boolean b36 = b34 & b35;
        boolean b37 = false;
        Label_0734: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress28 = GLContext.getFunctionAddress("glProgramUniformMatrix3dvEXT");
                this.glProgramUniformMatrix3dvEXT = functionAddress28;
                if (functionAddress28 == 0L) {
                    b37 = false;
                    break Label_0734;
                }
            }
            b37 = true;
        }
        final boolean b38 = b36 & b37;
        boolean b39 = false;
        Label_0768: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress29 = GLContext.getFunctionAddress("glProgramUniformMatrix4dvEXT");
                this.glProgramUniformMatrix4dvEXT = functionAddress29;
                if (functionAddress29 == 0L) {
                    b39 = false;
                    break Label_0768;
                }
            }
            b39 = true;
        }
        final boolean b40 = b38 & b39;
        boolean b41 = false;
        Label_0802: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress30 = GLContext.getFunctionAddress("glProgramUniformMatrix2x3dvEXT");
                this.glProgramUniformMatrix2x3dvEXT = functionAddress30;
                if (functionAddress30 == 0L) {
                    b41 = false;
                    break Label_0802;
                }
            }
            b41 = true;
        }
        final boolean b42 = b40 & b41;
        boolean b43 = false;
        Label_0836: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress31 = GLContext.getFunctionAddress("glProgramUniformMatrix2x4dvEXT");
                this.glProgramUniformMatrix2x4dvEXT = functionAddress31;
                if (functionAddress31 == 0L) {
                    b43 = false;
                    break Label_0836;
                }
            }
            b43 = true;
        }
        final boolean b44 = b42 & b43;
        boolean b45 = false;
        Label_0870: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress32 = GLContext.getFunctionAddress("glProgramUniformMatrix3x2dvEXT");
                this.glProgramUniformMatrix3x2dvEXT = functionAddress32;
                if (functionAddress32 == 0L) {
                    b45 = false;
                    break Label_0870;
                }
            }
            b45 = true;
        }
        final boolean b46 = b44 & b45;
        boolean b47 = false;
        Label_0904: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress33 = GLContext.getFunctionAddress("glProgramUniformMatrix3x4dvEXT");
                this.glProgramUniformMatrix3x4dvEXT = functionAddress33;
                if (functionAddress33 == 0L) {
                    b47 = false;
                    break Label_0904;
                }
            }
            b47 = true;
        }
        final boolean b48 = b46 & b47;
        boolean b49 = false;
        Label_0938: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress34 = GLContext.getFunctionAddress("glProgramUniformMatrix4x2dvEXT");
                this.glProgramUniformMatrix4x2dvEXT = functionAddress34;
                if (functionAddress34 == 0L) {
                    b49 = false;
                    break Label_0938;
                }
            }
            b49 = true;
        }
        final boolean b50 = b48 & b49;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress35 = GLContext.getFunctionAddress("glProgramUniformMatrix4x3dvEXT");
            this.glProgramUniformMatrix4x3dvEXT = functionAddress35;
            if (functionAddress35 == 0L) {
                final boolean b51 = false;
                return b50 & b51;
            }
        }
        final boolean b51 = true;
        return b50 & b51;
    }
    
    private boolean ARB_imaging_initNativeFunctionAddresses(final boolean b) {
        boolean b2 = false;
        Label_0025: {
            if (!b) {
                final long functionAddress = GLContext.getFunctionAddress("glColorTable");
                this.glColorTable = functionAddress;
                if (functionAddress == 0L) {
                    b2 = false;
                    break Label_0025;
                }
            }
            b2 = true;
        }
        boolean b3 = false;
        Label_0050: {
            if (!b) {
                final long functionAddress2 = GLContext.getFunctionAddress("glColorSubTable");
                this.glColorSubTable = functionAddress2;
                if (functionAddress2 == 0L) {
                    b3 = false;
                    break Label_0050;
                }
            }
            b3 = true;
        }
        final boolean b4 = b2 & b3;
        boolean b5 = false;
        Label_0076: {
            if (!b) {
                final long functionAddress3 = GLContext.getFunctionAddress("glColorTableParameteriv");
                this.glColorTableParameteriv = functionAddress3;
                if (functionAddress3 == 0L) {
                    b5 = false;
                    break Label_0076;
                }
            }
            b5 = true;
        }
        final boolean b6 = b4 & b5;
        boolean b7 = false;
        Label_0102: {
            if (!b) {
                final long functionAddress4 = GLContext.getFunctionAddress("glColorTableParameterfv");
                this.glColorTableParameterfv = functionAddress4;
                if (functionAddress4 == 0L) {
                    b7 = false;
                    break Label_0102;
                }
            }
            b7 = true;
        }
        final boolean b8 = b6 & b7;
        boolean b9 = false;
        Label_0128: {
            if (!b) {
                final long functionAddress5 = GLContext.getFunctionAddress("glCopyColorSubTable");
                this.glCopyColorSubTable = functionAddress5;
                if (functionAddress5 == 0L) {
                    b9 = false;
                    break Label_0128;
                }
            }
            b9 = true;
        }
        final boolean b10 = b8 & b9;
        boolean b11 = false;
        Label_0154: {
            if (!b) {
                final long functionAddress6 = GLContext.getFunctionAddress("glCopyColorTable");
                this.glCopyColorTable = functionAddress6;
                if (functionAddress6 == 0L) {
                    b11 = false;
                    break Label_0154;
                }
            }
            b11 = true;
        }
        final boolean b12 = b10 & b11;
        boolean b13 = false;
        Label_0180: {
            if (!b) {
                final long functionAddress7 = GLContext.getFunctionAddress("glGetColorTable");
                this.glGetColorTable = functionAddress7;
                if (functionAddress7 == 0L) {
                    b13 = false;
                    break Label_0180;
                }
            }
            b13 = true;
        }
        final boolean b14 = b12 & b13;
        boolean b15 = false;
        Label_0206: {
            if (!b) {
                final long functionAddress8 = GLContext.getFunctionAddress("glGetColorTableParameteriv");
                this.glGetColorTableParameteriv = functionAddress8;
                if (functionAddress8 == 0L) {
                    b15 = false;
                    break Label_0206;
                }
            }
            b15 = true;
        }
        final boolean b16 = b14 & b15;
        boolean b17 = false;
        Label_0232: {
            if (!b) {
                final long functionAddress9 = GLContext.getFunctionAddress("glGetColorTableParameterfv");
                this.glGetColorTableParameterfv = functionAddress9;
                if (functionAddress9 == 0L) {
                    b17 = false;
                    break Label_0232;
                }
            }
            b17 = true;
        }
        final boolean b18 = b16 & b17;
        final long functionAddress10 = GLContext.getFunctionAddress("glBlendEquation");
        this.glBlendEquation = functionAddress10;
        final boolean b19 = b18 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glBlendColor");
        this.glBlendColor = functionAddress11;
        final boolean b20 = b19 & functionAddress11 != 0L;
        boolean b21 = false;
        Label_0302: {
            if (!b) {
                final long functionAddress12 = GLContext.getFunctionAddress("glHistogram");
                this.glHistogram = functionAddress12;
                if (functionAddress12 == 0L) {
                    b21 = false;
                    break Label_0302;
                }
            }
            b21 = true;
        }
        final boolean b22 = b20 & b21;
        boolean b23 = false;
        Label_0328: {
            if (!b) {
                final long functionAddress13 = GLContext.getFunctionAddress("glResetHistogram");
                this.glResetHistogram = functionAddress13;
                if (functionAddress13 == 0L) {
                    b23 = false;
                    break Label_0328;
                }
            }
            b23 = true;
        }
        final boolean b24 = b22 & b23;
        boolean b25 = false;
        Label_0354: {
            if (!b) {
                final long functionAddress14 = GLContext.getFunctionAddress("glGetHistogram");
                this.glGetHistogram = functionAddress14;
                if (functionAddress14 == 0L) {
                    b25 = false;
                    break Label_0354;
                }
            }
            b25 = true;
        }
        final boolean b26 = b24 & b25;
        boolean b27 = false;
        Label_0380: {
            if (!b) {
                final long functionAddress15 = GLContext.getFunctionAddress("glGetHistogramParameterfv");
                this.glGetHistogramParameterfv = functionAddress15;
                if (functionAddress15 == 0L) {
                    b27 = false;
                    break Label_0380;
                }
            }
            b27 = true;
        }
        final boolean b28 = b26 & b27;
        boolean b29 = false;
        Label_0406: {
            if (!b) {
                final long functionAddress16 = GLContext.getFunctionAddress("glGetHistogramParameteriv");
                this.glGetHistogramParameteriv = functionAddress16;
                if (functionAddress16 == 0L) {
                    b29 = false;
                    break Label_0406;
                }
            }
            b29 = true;
        }
        final boolean b30 = b28 & b29;
        boolean b31 = false;
        Label_0432: {
            if (!b) {
                final long functionAddress17 = GLContext.getFunctionAddress("glMinmax");
                this.glMinmax = functionAddress17;
                if (functionAddress17 == 0L) {
                    b31 = false;
                    break Label_0432;
                }
            }
            b31 = true;
        }
        final boolean b32 = b30 & b31;
        boolean b33 = false;
        Label_0458: {
            if (!b) {
                final long functionAddress18 = GLContext.getFunctionAddress("glResetMinmax");
                this.glResetMinmax = functionAddress18;
                if (functionAddress18 == 0L) {
                    b33 = false;
                    break Label_0458;
                }
            }
            b33 = true;
        }
        final boolean b34 = b32 & b33;
        boolean b35 = false;
        Label_0484: {
            if (!b) {
                final long functionAddress19 = GLContext.getFunctionAddress("glGetMinmax");
                this.glGetMinmax = functionAddress19;
                if (functionAddress19 == 0L) {
                    b35 = false;
                    break Label_0484;
                }
            }
            b35 = true;
        }
        final boolean b36 = b34 & b35;
        boolean b37 = false;
        Label_0510: {
            if (!b) {
                final long functionAddress20 = GLContext.getFunctionAddress("glGetMinmaxParameterfv");
                this.glGetMinmaxParameterfv = functionAddress20;
                if (functionAddress20 == 0L) {
                    b37 = false;
                    break Label_0510;
                }
            }
            b37 = true;
        }
        final boolean b38 = b36 & b37;
        boolean b39 = false;
        Label_0536: {
            if (!b) {
                final long functionAddress21 = GLContext.getFunctionAddress("glGetMinmaxParameteriv");
                this.glGetMinmaxParameteriv = functionAddress21;
                if (functionAddress21 == 0L) {
                    b39 = false;
                    break Label_0536;
                }
            }
            b39 = true;
        }
        final boolean b40 = b38 & b39;
        boolean b41 = false;
        Label_0562: {
            if (!b) {
                final long functionAddress22 = GLContext.getFunctionAddress("glConvolutionFilter1D");
                this.glConvolutionFilter1D = functionAddress22;
                if (functionAddress22 == 0L) {
                    b41 = false;
                    break Label_0562;
                }
            }
            b41 = true;
        }
        final boolean b42 = b40 & b41;
        boolean b43 = false;
        Label_0588: {
            if (!b) {
                final long functionAddress23 = GLContext.getFunctionAddress("glConvolutionFilter2D");
                this.glConvolutionFilter2D = functionAddress23;
                if (functionAddress23 == 0L) {
                    b43 = false;
                    break Label_0588;
                }
            }
            b43 = true;
        }
        final boolean b44 = b42 & b43;
        boolean b45 = false;
        Label_0614: {
            if (!b) {
                final long functionAddress24 = GLContext.getFunctionAddress("glConvolutionParameterf");
                this.glConvolutionParameterf = functionAddress24;
                if (functionAddress24 == 0L) {
                    b45 = false;
                    break Label_0614;
                }
            }
            b45 = true;
        }
        final boolean b46 = b44 & b45;
        boolean b47 = false;
        Label_0640: {
            if (!b) {
                final long functionAddress25 = GLContext.getFunctionAddress("glConvolutionParameterfv");
                this.glConvolutionParameterfv = functionAddress25;
                if (functionAddress25 == 0L) {
                    b47 = false;
                    break Label_0640;
                }
            }
            b47 = true;
        }
        final boolean b48 = b46 & b47;
        boolean b49 = false;
        Label_0666: {
            if (!b) {
                final long functionAddress26 = GLContext.getFunctionAddress("glConvolutionParameteri");
                this.glConvolutionParameteri = functionAddress26;
                if (functionAddress26 == 0L) {
                    b49 = false;
                    break Label_0666;
                }
            }
            b49 = true;
        }
        final boolean b50 = b48 & b49;
        boolean b51 = false;
        Label_0692: {
            if (!b) {
                final long functionAddress27 = GLContext.getFunctionAddress("glConvolutionParameteriv");
                this.glConvolutionParameteriv = functionAddress27;
                if (functionAddress27 == 0L) {
                    b51 = false;
                    break Label_0692;
                }
            }
            b51 = true;
        }
        final boolean b52 = b50 & b51;
        boolean b53 = false;
        Label_0718: {
            if (!b) {
                final long functionAddress28 = GLContext.getFunctionAddress("glCopyConvolutionFilter1D");
                this.glCopyConvolutionFilter1D = functionAddress28;
                if (functionAddress28 == 0L) {
                    b53 = false;
                    break Label_0718;
                }
            }
            b53 = true;
        }
        final boolean b54 = b52 & b53;
        boolean b55 = false;
        Label_0744: {
            if (!b) {
                final long functionAddress29 = GLContext.getFunctionAddress("glCopyConvolutionFilter2D");
                this.glCopyConvolutionFilter2D = functionAddress29;
                if (functionAddress29 == 0L) {
                    b55 = false;
                    break Label_0744;
                }
            }
            b55 = true;
        }
        final boolean b56 = b54 & b55;
        boolean b57 = false;
        Label_0770: {
            if (!b) {
                final long functionAddress30 = GLContext.getFunctionAddress("glGetConvolutionFilter");
                this.glGetConvolutionFilter = functionAddress30;
                if (functionAddress30 == 0L) {
                    b57 = false;
                    break Label_0770;
                }
            }
            b57 = true;
        }
        final boolean b58 = b56 & b57;
        boolean b59 = false;
        Label_0796: {
            if (!b) {
                final long functionAddress31 = GLContext.getFunctionAddress("glGetConvolutionParameterfv");
                this.glGetConvolutionParameterfv = functionAddress31;
                if (functionAddress31 == 0L) {
                    b59 = false;
                    break Label_0796;
                }
            }
            b59 = true;
        }
        final boolean b60 = b58 & b59;
        boolean b61 = false;
        Label_0822: {
            if (!b) {
                final long functionAddress32 = GLContext.getFunctionAddress("glGetConvolutionParameteriv");
                this.glGetConvolutionParameteriv = functionAddress32;
                if (functionAddress32 == 0L) {
                    b61 = false;
                    break Label_0822;
                }
            }
            b61 = true;
        }
        final boolean b62 = b60 & b61;
        boolean b63 = false;
        Label_0848: {
            if (!b) {
                final long functionAddress33 = GLContext.getFunctionAddress("glSeparableFilter2D");
                this.glSeparableFilter2D = functionAddress33;
                if (functionAddress33 == 0L) {
                    b63 = false;
                    break Label_0848;
                }
            }
            b63 = true;
        }
        final boolean b64 = b62 & b63;
        if (!b) {
            final long functionAddress34 = GLContext.getFunctionAddress("glGetSeparableFilter");
            this.glGetSeparableFilter = functionAddress34;
            if (functionAddress34 == 0L) {
                final boolean b65 = false;
                return b64 & b65;
            }
        }
        final boolean b65 = true;
        return b64 & b65;
    }
    
    private boolean ARB_indirect_parameters_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMultiDrawArraysIndirectCountARB");
        this.glMultiDrawArraysIndirectCountARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMultiDrawElementsIndirectCountARB");
        this.glMultiDrawElementsIndirectCountARB = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_instanced_arrays_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexAttribDivisorARB");
        this.glVertexAttribDivisorARB = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_internalformat_query_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetInternalformativ");
        this.glGetInternalformativ = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_internalformat_query2_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetInternalformati64v");
        this.glGetInternalformati64v = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_invalidate_subdata_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glInvalidateTexSubImage");
        this.glInvalidateTexSubImage = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glInvalidateTexImage");
        this.glInvalidateTexImage = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glInvalidateBufferSubData");
        this.glInvalidateBufferSubData = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glInvalidateBufferData");
        this.glInvalidateBufferData = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glInvalidateFramebuffer");
        this.glInvalidateFramebuffer = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glInvalidateSubFramebuffer");
        this.glInvalidateSubFramebuffer = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean ARB_map_buffer_range_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMapBufferRange");
        this.glMapBufferRange = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glFlushMappedBufferRange");
        this.glFlushMappedBufferRange = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_matrix_palette_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glCurrentPaletteMatrixARB");
        this.glCurrentPaletteMatrixARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMatrixIndexPointerARB");
        this.glMatrixIndexPointerARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glMatrixIndexubvARB");
        this.glMatrixIndexubvARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glMatrixIndexusvARB");
        this.glMatrixIndexusvARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glMatrixIndexuivARB");
        this.glMatrixIndexuivARB = functionAddress5;
        return b4 & functionAddress5 != 0L;
    }
    
    private boolean ARB_multi_bind_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindBuffersBase");
        this.glBindBuffersBase = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBindBuffersRange");
        this.glBindBuffersRange = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glBindTextures");
        this.glBindTextures = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBindSamplers");
        this.glBindSamplers = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glBindImageTextures");
        this.glBindImageTextures = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glBindVertexBuffers");
        this.glBindVertexBuffers = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean ARB_multi_draw_indirect_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMultiDrawArraysIndirect");
        this.glMultiDrawArraysIndirect = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMultiDrawElementsIndirect");
        this.glMultiDrawElementsIndirect = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_multisample_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glSampleCoverageARB");
        this.glSampleCoverageARB = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_multitexture_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glClientActiveTextureARB");
        this.glClientActiveTextureARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glActiveTextureARB");
        this.glActiveTextureARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glMultiTexCoord1fARB");
        this.glMultiTexCoord1fARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glMultiTexCoord1dARB");
        this.glMultiTexCoord1dARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glMultiTexCoord1iARB");
        this.glMultiTexCoord1iARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glMultiTexCoord1sARB");
        this.glMultiTexCoord1sARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glMultiTexCoord2fARB");
        this.glMultiTexCoord2fARB = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glMultiTexCoord2dARB");
        this.glMultiTexCoord2dARB = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glMultiTexCoord2iARB");
        this.glMultiTexCoord2iARB = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glMultiTexCoord2sARB");
        this.glMultiTexCoord2sARB = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glMultiTexCoord3fARB");
        this.glMultiTexCoord3fARB = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glMultiTexCoord3dARB");
        this.glMultiTexCoord3dARB = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glMultiTexCoord3iARB");
        this.glMultiTexCoord3iARB = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glMultiTexCoord3sARB");
        this.glMultiTexCoord3sARB = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glMultiTexCoord4fARB");
        this.glMultiTexCoord4fARB = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glMultiTexCoord4dARB");
        this.glMultiTexCoord4dARB = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glMultiTexCoord4iARB");
        this.glMultiTexCoord4iARB = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glMultiTexCoord4sARB");
        this.glMultiTexCoord4sARB = functionAddress18;
        return b17 & functionAddress18 != 0L;
    }
    
    private boolean ARB_occlusion_query_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGenQueriesARB");
        this.glGenQueriesARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteQueriesARB");
        this.glDeleteQueriesARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glIsQueryARB");
        this.glIsQueryARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBeginQueryARB");
        this.glBeginQueryARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glEndQueryARB");
        this.glEndQueryARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetQueryivARB");
        this.glGetQueryivARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetQueryObjectivARB");
        this.glGetQueryObjectivARB = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetQueryObjectuivARB");
        this.glGetQueryObjectuivARB = functionAddress8;
        return b7 & functionAddress8 != 0L;
    }
    
    private boolean ARB_point_parameters_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glPointParameterfARB");
        this.glPointParameterfARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glPointParameterfvARB");
        this.glPointParameterfvARB = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_program_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProgramStringARB");
        this.glProgramStringARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBindProgramARB");
        this.glBindProgramARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDeleteProgramsARB");
        this.glDeleteProgramsARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGenProgramsARB");
        this.glGenProgramsARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glProgramEnvParameter4fARB");
        this.glProgramEnvParameter4fARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glProgramEnvParameter4dARB");
        this.glProgramEnvParameter4dARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glProgramEnvParameter4fvARB");
        this.glProgramEnvParameter4fvARB = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glProgramEnvParameter4dvARB");
        this.glProgramEnvParameter4dvARB = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glProgramLocalParameter4fARB");
        this.glProgramLocalParameter4fARB = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glProgramLocalParameter4dARB");
        this.glProgramLocalParameter4dARB = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glProgramLocalParameter4fvARB");
        this.glProgramLocalParameter4fvARB = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glProgramLocalParameter4dvARB");
        this.glProgramLocalParameter4dvARB = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glGetProgramEnvParameterfvARB");
        this.glGetProgramEnvParameterfvARB = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glGetProgramEnvParameterdvARB");
        this.glGetProgramEnvParameterdvARB = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glGetProgramLocalParameterfvARB");
        this.glGetProgramLocalParameterfvARB = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glGetProgramLocalParameterdvARB");
        this.glGetProgramLocalParameterdvARB = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glGetProgramivARB");
        this.glGetProgramivARB = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetProgramStringARB");
        this.glGetProgramStringARB = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glIsProgramARB");
        this.glIsProgramARB = functionAddress19;
        return b18 & functionAddress19 != 0L;
    }
    
    private boolean ARB_program_interface_query_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetProgramInterfaceiv");
        this.glGetProgramInterfaceiv = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetProgramResourceIndex");
        this.glGetProgramResourceIndex = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetProgramResourceName");
        this.glGetProgramResourceName = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetProgramResourceiv");
        this.glGetProgramResourceiv = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetProgramResourceLocation");
        this.glGetProgramResourceLocation = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetProgramResourceLocationIndex");
        this.glGetProgramResourceLocationIndex = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean ARB_provoking_vertex_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProvokingVertex");
        this.glProvokingVertex = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_robustness_initNativeFunctionAddresses(final boolean b, final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glGetGraphicsResetStatusARB");
        this.glGetGraphicsResetStatusARB = functionAddress;
        final boolean b2 = functionAddress != 0L;
        boolean b3 = false;
        Label_0046: {
            if (!b) {
                final long functionAddress2 = GLContext.getFunctionAddress("glGetnMapdvARB");
                this.glGetnMapdvARB = functionAddress2;
                if (functionAddress2 == 0L) {
                    b3 = false;
                    break Label_0046;
                }
            }
            b3 = true;
        }
        final boolean b4 = b2 & b3;
        boolean b5 = false;
        Label_0072: {
            if (!b) {
                final long functionAddress3 = GLContext.getFunctionAddress("glGetnMapfvARB");
                this.glGetnMapfvARB = functionAddress3;
                if (functionAddress3 == 0L) {
                    b5 = false;
                    break Label_0072;
                }
            }
            b5 = true;
        }
        final boolean b6 = b4 & b5;
        boolean b7 = false;
        Label_0098: {
            if (!b) {
                final long functionAddress4 = GLContext.getFunctionAddress("glGetnMapivARB");
                this.glGetnMapivARB = functionAddress4;
                if (functionAddress4 == 0L) {
                    b7 = false;
                    break Label_0098;
                }
            }
            b7 = true;
        }
        final boolean b8 = b6 & b7;
        boolean b9 = false;
        Label_0124: {
            if (!b) {
                final long functionAddress5 = GLContext.getFunctionAddress("glGetnPixelMapfvARB");
                this.glGetnPixelMapfvARB = functionAddress5;
                if (functionAddress5 == 0L) {
                    b9 = false;
                    break Label_0124;
                }
            }
            b9 = true;
        }
        final boolean b10 = b8 & b9;
        boolean b11 = false;
        Label_0150: {
            if (!b) {
                final long functionAddress6 = GLContext.getFunctionAddress("glGetnPixelMapuivARB");
                this.glGetnPixelMapuivARB = functionAddress6;
                if (functionAddress6 == 0L) {
                    b11 = false;
                    break Label_0150;
                }
            }
            b11 = true;
        }
        final boolean b12 = b10 & b11;
        boolean b13 = false;
        Label_0176: {
            if (!b) {
                final long functionAddress7 = GLContext.getFunctionAddress("glGetnPixelMapusvARB");
                this.glGetnPixelMapusvARB = functionAddress7;
                if (functionAddress7 == 0L) {
                    b13 = false;
                    break Label_0176;
                }
            }
            b13 = true;
        }
        final boolean b14 = b12 & b13;
        boolean b15 = false;
        Label_0202: {
            if (!b) {
                final long functionAddress8 = GLContext.getFunctionAddress("glGetnPolygonStippleARB");
                this.glGetnPolygonStippleARB = functionAddress8;
                if (functionAddress8 == 0L) {
                    b15 = false;
                    break Label_0202;
                }
            }
            b15 = true;
        }
        final boolean b16 = b14 & b15;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetnTexImageARB");
        this.glGetnTexImageARB = functionAddress9;
        final boolean b17 = b16 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glReadnPixelsARB");
        this.glReadnPixelsARB = functionAddress10;
        final boolean b18 = b17 & functionAddress10 != 0L;
        boolean b19 = false;
        Label_0280: {
            if (set.contains("GL_ARB_imaging")) {
                final long functionAddress11 = GLContext.getFunctionAddress("glGetnColorTableARB");
                this.glGetnColorTableARB = functionAddress11;
                if (functionAddress11 == 0L) {
                    b19 = false;
                    break Label_0280;
                }
            }
            b19 = true;
        }
        final boolean b20 = b18 & b19;
        boolean b21 = false;
        Label_0314: {
            if (set.contains("GL_ARB_imaging")) {
                final long functionAddress12 = GLContext.getFunctionAddress("glGetnConvolutionFilterARB");
                this.glGetnConvolutionFilterARB = functionAddress12;
                if (functionAddress12 == 0L) {
                    b21 = false;
                    break Label_0314;
                }
            }
            b21 = true;
        }
        final boolean b22 = b20 & b21;
        boolean b23 = false;
        Label_0348: {
            if (set.contains("GL_ARB_imaging")) {
                final long functionAddress13 = GLContext.getFunctionAddress("glGetnSeparableFilterARB");
                this.glGetnSeparableFilterARB = functionAddress13;
                if (functionAddress13 == 0L) {
                    b23 = false;
                    break Label_0348;
                }
            }
            b23 = true;
        }
        final boolean b24 = b22 & b23;
        boolean b25 = false;
        Label_0382: {
            if (set.contains("GL_ARB_imaging")) {
                final long functionAddress14 = GLContext.getFunctionAddress("glGetnHistogramARB");
                this.glGetnHistogramARB = functionAddress14;
                if (functionAddress14 == 0L) {
                    b25 = false;
                    break Label_0382;
                }
            }
            b25 = true;
        }
        final boolean b26 = b24 & b25;
        boolean b27 = false;
        Label_0416: {
            if (set.contains("GL_ARB_imaging")) {
                final long functionAddress15 = GLContext.getFunctionAddress("glGetnMinmaxARB");
                this.glGetnMinmaxARB = functionAddress15;
                if (functionAddress15 == 0L) {
                    b27 = false;
                    break Label_0416;
                }
            }
            b27 = true;
        }
        final boolean b28 = b26 & b27;
        boolean b29 = false;
        Label_0450: {
            if (set.contains("OpenGL13")) {
                final long functionAddress16 = GLContext.getFunctionAddress("glGetnCompressedTexImageARB");
                this.glGetnCompressedTexImageARB = functionAddress16;
                if (functionAddress16 == 0L) {
                    b29 = false;
                    break Label_0450;
                }
            }
            b29 = true;
        }
        final boolean b30 = b28 & b29;
        boolean b31 = false;
        Label_0484: {
            if (set.contains("OpenGL20")) {
                final long functionAddress17 = GLContext.getFunctionAddress("glGetnUniformfvARB");
                this.glGetnUniformfvARB = functionAddress17;
                if (functionAddress17 == 0L) {
                    b31 = false;
                    break Label_0484;
                }
            }
            b31 = true;
        }
        final boolean b32 = b30 & b31;
        boolean b33 = false;
        Label_0518: {
            if (set.contains("OpenGL20")) {
                final long functionAddress18 = GLContext.getFunctionAddress("glGetnUniformivARB");
                this.glGetnUniformivARB = functionAddress18;
                if (functionAddress18 == 0L) {
                    b33 = false;
                    break Label_0518;
                }
            }
            b33 = true;
        }
        final boolean b34 = b32 & b33;
        boolean b35 = false;
        Label_0552: {
            if (set.contains("OpenGL20")) {
                final long functionAddress19 = GLContext.getFunctionAddress("glGetnUniformuivARB");
                this.glGetnUniformuivARB = functionAddress19;
                if (functionAddress19 == 0L) {
                    b35 = false;
                    break Label_0552;
                }
            }
            b35 = true;
        }
        final boolean b36 = b34 & b35;
        if (set.contains("OpenGL20")) {
            final long functionAddress20 = GLContext.getFunctionAddress("glGetnUniformdvARB");
            this.glGetnUniformdvARB = functionAddress20;
            if (functionAddress20 == 0L) {
                final boolean b37 = false;
                return b36 & b37;
            }
        }
        final boolean b37 = true;
        return b36 & b37;
    }
    
    private boolean ARB_sample_shading_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMinSampleShadingARB");
        this.glMinSampleShadingARB = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_sampler_objects_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGenSamplers");
        this.glGenSamplers = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteSamplers");
        this.glDeleteSamplers = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glIsSampler");
        this.glIsSampler = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBindSampler");
        this.glBindSampler = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glSamplerParameteri");
        this.glSamplerParameteri = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glSamplerParameterf");
        this.glSamplerParameterf = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glSamplerParameteriv");
        this.glSamplerParameteriv = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glSamplerParameterfv");
        this.glSamplerParameterfv = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glSamplerParameterIiv");
        this.glSamplerParameterIiv = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glSamplerParameterIuiv");
        this.glSamplerParameterIuiv = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGetSamplerParameteriv");
        this.glGetSamplerParameteriv = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glGetSamplerParameterfv");
        this.glGetSamplerParameterfv = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glGetSamplerParameterIiv");
        this.glGetSamplerParameterIiv = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glGetSamplerParameterIuiv");
        this.glGetSamplerParameterIuiv = functionAddress14;
        return b13 & functionAddress14 != 0L;
    }
    
    private boolean ARB_separate_shader_objects_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glUseProgramStages");
        this.glUseProgramStages = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glActiveShaderProgram");
        this.glActiveShaderProgram = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glCreateShaderProgramv");
        this.glCreateShaderProgramv = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBindProgramPipeline");
        this.glBindProgramPipeline = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glDeleteProgramPipelines");
        this.glDeleteProgramPipelines = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGenProgramPipelines");
        this.glGenProgramPipelines = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glIsProgramPipeline");
        this.glIsProgramPipeline = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glProgramParameteri");
        this.glProgramParameteri = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetProgramPipelineiv");
        this.glGetProgramPipelineiv = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glProgramUniform1i");
        this.glProgramUniform1i = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glProgramUniform2i");
        this.glProgramUniform2i = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glProgramUniform3i");
        this.glProgramUniform3i = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glProgramUniform4i");
        this.glProgramUniform4i = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glProgramUniform1f");
        this.glProgramUniform1f = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glProgramUniform2f");
        this.glProgramUniform2f = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glProgramUniform3f");
        this.glProgramUniform3f = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glProgramUniform4f");
        this.glProgramUniform4f = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glProgramUniform1d");
        this.glProgramUniform1d = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glProgramUniform2d");
        this.glProgramUniform2d = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glProgramUniform3d");
        this.glProgramUniform3d = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glProgramUniform4d");
        this.glProgramUniform4d = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glProgramUniform1iv");
        this.glProgramUniform1iv = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glProgramUniform2iv");
        this.glProgramUniform2iv = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glProgramUniform3iv");
        this.glProgramUniform3iv = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glProgramUniform4iv");
        this.glProgramUniform4iv = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glProgramUniform1fv");
        this.glProgramUniform1fv = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glProgramUniform2fv");
        this.glProgramUniform2fv = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glProgramUniform3fv");
        this.glProgramUniform3fv = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glProgramUniform4fv");
        this.glProgramUniform4fv = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glProgramUniform1dv");
        this.glProgramUniform1dv = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glProgramUniform2dv");
        this.glProgramUniform2dv = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glProgramUniform3dv");
        this.glProgramUniform3dv = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glProgramUniform4dv");
        this.glProgramUniform4dv = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glProgramUniform1ui");
        this.glProgramUniform1ui = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glProgramUniform2ui");
        this.glProgramUniform2ui = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glProgramUniform3ui");
        this.glProgramUniform3ui = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glProgramUniform4ui");
        this.glProgramUniform4ui = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glProgramUniform1uiv");
        this.glProgramUniform1uiv = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glProgramUniform2uiv");
        this.glProgramUniform2uiv = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glProgramUniform3uiv");
        this.glProgramUniform3uiv = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glProgramUniform4uiv");
        this.glProgramUniform4uiv = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glProgramUniformMatrix2fv");
        this.glProgramUniformMatrix2fv = functionAddress42;
        final boolean b42 = b41 & functionAddress42 != 0L;
        final long functionAddress43 = GLContext.getFunctionAddress("glProgramUniformMatrix3fv");
        this.glProgramUniformMatrix3fv = functionAddress43;
        final boolean b43 = b42 & functionAddress43 != 0L;
        final long functionAddress44 = GLContext.getFunctionAddress("glProgramUniformMatrix4fv");
        this.glProgramUniformMatrix4fv = functionAddress44;
        final boolean b44 = b43 & functionAddress44 != 0L;
        final long functionAddress45 = GLContext.getFunctionAddress("glProgramUniformMatrix2dv");
        this.glProgramUniformMatrix2dv = functionAddress45;
        final boolean b45 = b44 & functionAddress45 != 0L;
        final long functionAddress46 = GLContext.getFunctionAddress("glProgramUniformMatrix3dv");
        this.glProgramUniformMatrix3dv = functionAddress46;
        final boolean b46 = b45 & functionAddress46 != 0L;
        final long functionAddress47 = GLContext.getFunctionAddress("glProgramUniformMatrix4dv");
        this.glProgramUniformMatrix4dv = functionAddress47;
        final boolean b47 = b46 & functionAddress47 != 0L;
        final long functionAddress48 = GLContext.getFunctionAddress("glProgramUniformMatrix2x3fv");
        this.glProgramUniformMatrix2x3fv = functionAddress48;
        final boolean b48 = b47 & functionAddress48 != 0L;
        final long functionAddress49 = GLContext.getFunctionAddress("glProgramUniformMatrix3x2fv");
        this.glProgramUniformMatrix3x2fv = functionAddress49;
        final boolean b49 = b48 & functionAddress49 != 0L;
        final long functionAddress50 = GLContext.getFunctionAddress("glProgramUniformMatrix2x4fv");
        this.glProgramUniformMatrix2x4fv = functionAddress50;
        final boolean b50 = b49 & functionAddress50 != 0L;
        final long functionAddress51 = GLContext.getFunctionAddress("glProgramUniformMatrix4x2fv");
        this.glProgramUniformMatrix4x2fv = functionAddress51;
        final boolean b51 = b50 & functionAddress51 != 0L;
        final long functionAddress52 = GLContext.getFunctionAddress("glProgramUniformMatrix3x4fv");
        this.glProgramUniformMatrix3x4fv = functionAddress52;
        final boolean b52 = b51 & functionAddress52 != 0L;
        final long functionAddress53 = GLContext.getFunctionAddress("glProgramUniformMatrix4x3fv");
        this.glProgramUniformMatrix4x3fv = functionAddress53;
        final boolean b53 = b52 & functionAddress53 != 0L;
        final long functionAddress54 = GLContext.getFunctionAddress("glProgramUniformMatrix2x3dv");
        this.glProgramUniformMatrix2x3dv = functionAddress54;
        final boolean b54 = b53 & functionAddress54 != 0L;
        final long functionAddress55 = GLContext.getFunctionAddress("glProgramUniformMatrix3x2dv");
        this.glProgramUniformMatrix3x2dv = functionAddress55;
        final boolean b55 = b54 & functionAddress55 != 0L;
        final long functionAddress56 = GLContext.getFunctionAddress("glProgramUniformMatrix2x4dv");
        this.glProgramUniformMatrix2x4dv = functionAddress56;
        final boolean b56 = b55 & functionAddress56 != 0L;
        final long functionAddress57 = GLContext.getFunctionAddress("glProgramUniformMatrix4x2dv");
        this.glProgramUniformMatrix4x2dv = functionAddress57;
        final boolean b57 = b56 & functionAddress57 != 0L;
        final long functionAddress58 = GLContext.getFunctionAddress("glProgramUniformMatrix3x4dv");
        this.glProgramUniformMatrix3x4dv = functionAddress58;
        final boolean b58 = b57 & functionAddress58 != 0L;
        final long functionAddress59 = GLContext.getFunctionAddress("glProgramUniformMatrix4x3dv");
        this.glProgramUniformMatrix4x3dv = functionAddress59;
        final boolean b59 = b58 & functionAddress59 != 0L;
        final long functionAddress60 = GLContext.getFunctionAddress("glValidateProgramPipeline");
        this.glValidateProgramPipeline = functionAddress60;
        final boolean b60 = b59 & functionAddress60 != 0L;
        final long functionAddress61 = GLContext.getFunctionAddress("glGetProgramPipelineInfoLog");
        this.glGetProgramPipelineInfoLog = functionAddress61;
        return b60 & functionAddress61 != 0L;
    }
    
    private boolean ARB_shader_atomic_counters_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetActiveAtomicCounterBufferiv");
        this.glGetActiveAtomicCounterBufferiv = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_shader_image_load_store_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindImageTexture");
        this.glBindImageTexture = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMemoryBarrier");
        this.glMemoryBarrier = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_shader_objects_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDeleteObjectARB");
        this.glDeleteObjectARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetHandleARB");
        this.glGetHandleARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDetachObjectARB");
        this.glDetachObjectARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glCreateShaderObjectARB");
        this.glCreateShaderObjectARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glShaderSourceARB");
        this.glShaderSourceARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glCompileShaderARB");
        this.glCompileShaderARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glCreateProgramObjectARB");
        this.glCreateProgramObjectARB = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glAttachObjectARB");
        this.glAttachObjectARB = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glLinkProgramARB");
        this.glLinkProgramARB = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glUseProgramObjectARB");
        this.glUseProgramObjectARB = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glValidateProgramARB");
        this.glValidateProgramARB = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glUniform1fARB");
        this.glUniform1fARB = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glUniform2fARB");
        this.glUniform2fARB = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glUniform3fARB");
        this.glUniform3fARB = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glUniform4fARB");
        this.glUniform4fARB = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glUniform1iARB");
        this.glUniform1iARB = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glUniform2iARB");
        this.glUniform2iARB = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glUniform3iARB");
        this.glUniform3iARB = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glUniform4iARB");
        this.glUniform4iARB = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glUniform1fvARB");
        this.glUniform1fvARB = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glUniform2fvARB");
        this.glUniform2fvARB = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glUniform3fvARB");
        this.glUniform3fvARB = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glUniform4fvARB");
        this.glUniform4fvARB = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glUniform1ivARB");
        this.glUniform1ivARB = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glUniform2ivARB");
        this.glUniform2ivARB = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glUniform3ivARB");
        this.glUniform3ivARB = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glUniform4ivARB");
        this.glUniform4ivARB = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glUniformMatrix2fvARB");
        this.glUniformMatrix2fvARB = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glUniformMatrix3fvARB");
        this.glUniformMatrix3fvARB = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glUniformMatrix4fvARB");
        this.glUniformMatrix4fvARB = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glGetObjectParameterfvARB");
        this.glGetObjectParameterfvARB = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glGetObjectParameterivARB");
        this.glGetObjectParameterivARB = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glGetInfoLogARB");
        this.glGetInfoLogARB = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glGetAttachedObjectsARB");
        this.glGetAttachedObjectsARB = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glGetUniformLocationARB");
        this.glGetUniformLocationARB = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glGetActiveUniformARB");
        this.glGetActiveUniformARB = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glGetUniformfvARB");
        this.glGetUniformfvARB = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glGetUniformivARB");
        this.glGetUniformivARB = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glGetShaderSourceARB");
        this.glGetShaderSourceARB = functionAddress39;
        return b38 & functionAddress39 != 0L;
    }
    
    private boolean ARB_shader_storage_buffer_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glShaderStorageBlockBinding");
        this.glShaderStorageBlockBinding = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_shader_subroutine_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetSubroutineUniformLocation");
        this.glGetSubroutineUniformLocation = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetSubroutineIndex");
        this.glGetSubroutineIndex = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetActiveSubroutineUniformiv");
        this.glGetActiveSubroutineUniformiv = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetActiveSubroutineUniformName");
        this.glGetActiveSubroutineUniformName = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetActiveSubroutineName");
        this.glGetActiveSubroutineName = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glUniformSubroutinesuiv");
        this.glUniformSubroutinesuiv = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetUniformSubroutineuiv");
        this.glGetUniformSubroutineuiv = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetProgramStageiv");
        this.glGetProgramStageiv = functionAddress8;
        return b7 & functionAddress8 != 0L;
    }
    
    private boolean ARB_shading_language_include_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glNamedStringARB");
        this.glNamedStringARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteNamedStringARB");
        this.glDeleteNamedStringARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glCompileShaderIncludeARB");
        this.glCompileShaderIncludeARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glIsNamedStringARB");
        this.glIsNamedStringARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetNamedStringARB");
        this.glGetNamedStringARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetNamedStringivARB");
        this.glGetNamedStringivARB = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean ARB_sparse_buffer_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBufferPageCommitmentARB");
        this.glBufferPageCommitmentARB = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_sparse_texture_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glTexPageCommitmentARB");
        this.glTexPageCommitmentARB = functionAddress;
        final boolean b = functionAddress != 0L;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress2 = GLContext.getFunctionAddress("glTexturePageCommitmentEXT");
            this.glTexturePageCommitmentEXT = functionAddress2;
            if (functionAddress2 == 0L) {
                final boolean b2 = false;
                return b & b2;
            }
        }
        final boolean b2 = true;
        return b & b2;
    }
    
    private boolean ARB_sync_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glFenceSync");
        this.glFenceSync = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glIsSync");
        this.glIsSync = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDeleteSync");
        this.glDeleteSync = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glClientWaitSync");
        this.glClientWaitSync = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glWaitSync");
        this.glWaitSync = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetInteger64v");
        this.glGetInteger64v = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetSynciv");
        this.glGetSynciv = functionAddress7;
        return b6 & functionAddress7 != 0L;
    }
    
    private boolean ARB_tessellation_shader_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glPatchParameteri");
        this.glPatchParameteri = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glPatchParameterfv");
        this.glPatchParameterfv = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_texture_barrier_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTextureBarrier");
        this.glTextureBarrier = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_texture_buffer_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTexBufferARB");
        this.glTexBufferARB = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_texture_buffer_range_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glTexBufferRange");
        this.glTexBufferRange = functionAddress;
        final boolean b = functionAddress != 0L;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress2 = GLContext.getFunctionAddress("glTextureBufferRangeEXT");
            this.glTextureBufferRangeEXT = functionAddress2;
            if (functionAddress2 == 0L) {
                final boolean b2 = false;
                return b & b2;
            }
        }
        final boolean b2 = true;
        return b & b2;
    }
    
    private boolean ARB_texture_compression_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glCompressedTexImage1DARB");
        this.glCompressedTexImage1DARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glCompressedTexImage2DARB");
        this.glCompressedTexImage2DARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glCompressedTexImage3DARB");
        this.glCompressedTexImage3DARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glCompressedTexSubImage1DARB");
        this.glCompressedTexSubImage1DARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glCompressedTexSubImage2DARB");
        this.glCompressedTexSubImage2DARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glCompressedTexSubImage3DARB");
        this.glCompressedTexSubImage3DARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetCompressedTexImageARB");
        this.glGetCompressedTexImageARB = functionAddress7;
        return b6 & functionAddress7 != 0L;
    }
    
    private boolean ARB_texture_multisample_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTexImage2DMultisample");
        this.glTexImage2DMultisample = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glTexImage3DMultisample");
        this.glTexImage3DMultisample = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetMultisamplefv");
        this.glGetMultisamplefv = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glSampleMaski");
        this.glSampleMaski = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean ARB_texture_storage_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress(new String[] { "glTexStorage1D", "glTexStorage1DEXT" });
        this.glTexStorage1D = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress(new String[] { "glTexStorage2D", "glTexStorage2DEXT" });
        this.glTexStorage2D = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress(new String[] { "glTexStorage3D", "glTexStorage3DEXT" });
        this.glTexStorage3D = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        boolean b4 = false;
        Label_0150: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress4 = GLContext.getFunctionAddress(new String[] { "glTextureStorage1DEXT", "glTextureStorage1DEXTEXT" });
                this.glTextureStorage1DEXT = functionAddress4;
                if (functionAddress4 == 0L) {
                    b4 = false;
                    break Label_0150;
                }
            }
            b4 = true;
        }
        final boolean b5 = b3 & b4;
        boolean b6 = false;
        Label_0197: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress5 = GLContext.getFunctionAddress(new String[] { "glTextureStorage2DEXT", "glTextureStorage2DEXTEXT" });
                this.glTextureStorage2DEXT = functionAddress5;
                if (functionAddress5 == 0L) {
                    b6 = false;
                    break Label_0197;
                }
            }
            b6 = true;
        }
        final boolean b7 = b5 & b6;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress6 = GLContext.getFunctionAddress(new String[] { "glTextureStorage3DEXT", "glTextureStorage3DEXTEXT" });
            this.glTextureStorage3DEXT = functionAddress6;
            if (functionAddress6 == 0L) {
                final boolean b8 = false;
                return b7 & b8;
            }
        }
        final boolean b8 = true;
        return b7 & b8;
    }
    
    private boolean ARB_texture_storage_multisample_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glTexStorage2DMultisample");
        this.glTexStorage2DMultisample = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glTexStorage3DMultisample");
        this.glTexStorage3DMultisample = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        boolean b3 = false;
        Label_0076: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress3 = GLContext.getFunctionAddress("glTextureStorage2DMultisampleEXT");
                this.glTextureStorage2DMultisampleEXT = functionAddress3;
                if (functionAddress3 == 0L) {
                    b3 = false;
                    break Label_0076;
                }
            }
            b3 = true;
        }
        final boolean b4 = b2 & b3;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress4 = GLContext.getFunctionAddress("glTextureStorage3DMultisampleEXT");
            this.glTextureStorage3DMultisampleEXT = functionAddress4;
            if (functionAddress4 == 0L) {
                final boolean b5 = false;
                return b4 & b5;
            }
        }
        final boolean b5 = true;
        return b4 & b5;
    }
    
    private boolean ARB_texture_view_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTextureView");
        this.glTextureView = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ARB_timer_query_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glQueryCounter");
        this.glQueryCounter = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetQueryObjecti64v");
        this.glGetQueryObjecti64v = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetQueryObjectui64v");
        this.glGetQueryObjectui64v = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean ARB_transform_feedback2_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindTransformFeedback");
        this.glBindTransformFeedback = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteTransformFeedbacks");
        this.glDeleteTransformFeedbacks = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGenTransformFeedbacks");
        this.glGenTransformFeedbacks = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glIsTransformFeedback");
        this.glIsTransformFeedback = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glPauseTransformFeedback");
        this.glPauseTransformFeedback = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glResumeTransformFeedback");
        this.glResumeTransformFeedback = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glDrawTransformFeedback");
        this.glDrawTransformFeedback = functionAddress7;
        return b6 & functionAddress7 != 0L;
    }
    
    private boolean ARB_transform_feedback3_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawTransformFeedbackStream");
        this.glDrawTransformFeedbackStream = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBeginQueryIndexed");
        this.glBeginQueryIndexed = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glEndQueryIndexed");
        this.glEndQueryIndexed = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetQueryIndexediv");
        this.glGetQueryIndexediv = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean ARB_transform_feedback_instanced_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawTransformFeedbackInstanced");
        this.glDrawTransformFeedbackInstanced = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawTransformFeedbackStreamInstanced");
        this.glDrawTransformFeedbackStreamInstanced = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_transpose_matrix_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glLoadTransposeMatrixfARB");
        this.glLoadTransposeMatrixfARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMultTransposeMatrixfARB");
        this.glMultTransposeMatrixfARB = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ARB_uniform_buffer_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetUniformIndices");
        this.glGetUniformIndices = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetActiveUniformsiv");
        this.glGetActiveUniformsiv = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetActiveUniformName");
        this.glGetActiveUniformName = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetUniformBlockIndex");
        this.glGetUniformBlockIndex = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetActiveUniformBlockiv");
        this.glGetActiveUniformBlockiv = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetActiveUniformBlockName");
        this.glGetActiveUniformBlockName = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glBindBufferRange");
        this.glBindBufferRange = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glBindBufferBase");
        this.glBindBufferBase = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetIntegeri_v");
        this.glGetIntegeri_v = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glUniformBlockBinding");
        this.glUniformBlockBinding = functionAddress10;
        return b9 & functionAddress10 != 0L;
    }
    
    private boolean ARB_vertex_array_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindVertexArray");
        this.glBindVertexArray = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteVertexArrays");
        this.glDeleteVertexArrays = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGenVertexArrays");
        this.glGenVertexArrays = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glIsVertexArray");
        this.glIsVertexArray = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean ARB_vertex_attrib_64bit_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glVertexAttribL1d");
        this.glVertexAttribL1d = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexAttribL2d");
        this.glVertexAttribL2d = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexAttribL3d");
        this.glVertexAttribL3d = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glVertexAttribL4d");
        this.glVertexAttribL4d = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glVertexAttribL1dv");
        this.glVertexAttribL1dv = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexAttribL2dv");
        this.glVertexAttribL2dv = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glVertexAttribL3dv");
        this.glVertexAttribL3dv = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glVertexAttribL4dv");
        this.glVertexAttribL4dv = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glVertexAttribLPointer");
        this.glVertexAttribLPointer = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glGetVertexAttribLdv");
        this.glGetVertexAttribLdv = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress11 = GLContext.getFunctionAddress("glVertexArrayVertexAttribLOffsetEXT");
            this.glVertexArrayVertexAttribLOffsetEXT = functionAddress11;
            if (functionAddress11 == 0L) {
                final boolean b11 = false;
                return b10 & b11;
            }
        }
        final boolean b11 = true;
        return b10 & b11;
    }
    
    private boolean ARB_vertex_attrib_binding_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindVertexBuffer");
        this.glBindVertexBuffer = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexAttribFormat");
        this.glVertexAttribFormat = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexAttribIFormat");
        this.glVertexAttribIFormat = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glVertexAttribLFormat");
        this.glVertexAttribLFormat = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glVertexAttribBinding");
        this.glVertexAttribBinding = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexBindingDivisor");
        this.glVertexBindingDivisor = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean ARB_vertex_blend_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glWeightbvARB");
        this.glWeightbvARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glWeightsvARB");
        this.glWeightsvARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glWeightivARB");
        this.glWeightivARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glWeightfvARB");
        this.glWeightfvARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glWeightdvARB");
        this.glWeightdvARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glWeightubvARB");
        this.glWeightubvARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glWeightusvARB");
        this.glWeightusvARB = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glWeightuivARB");
        this.glWeightuivARB = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glWeightPointerARB");
        this.glWeightPointerARB = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVertexBlendARB");
        this.glVertexBlendARB = functionAddress10;
        return b9 & functionAddress10 != 0L;
    }
    
    private boolean ARB_vertex_program_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexAttrib1sARB");
        this.glVertexAttrib1sARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexAttrib1fARB");
        this.glVertexAttrib1fARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexAttrib1dARB");
        this.glVertexAttrib1dARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glVertexAttrib2sARB");
        this.glVertexAttrib2sARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glVertexAttrib2fARB");
        this.glVertexAttrib2fARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexAttrib2dARB");
        this.glVertexAttrib2dARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glVertexAttrib3sARB");
        this.glVertexAttrib3sARB = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glVertexAttrib3fARB");
        this.glVertexAttrib3fARB = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glVertexAttrib3dARB");
        this.glVertexAttrib3dARB = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVertexAttrib4sARB");
        this.glVertexAttrib4sARB = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glVertexAttrib4fARB");
        this.glVertexAttrib4fARB = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glVertexAttrib4dARB");
        this.glVertexAttrib4dARB = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glVertexAttrib4NubARB");
        this.glVertexAttrib4NubARB = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glVertexAttribPointerARB");
        this.glVertexAttribPointerARB = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glEnableVertexAttribArrayARB");
        this.glEnableVertexAttribArrayARB = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glDisableVertexAttribArrayARB");
        this.glDisableVertexAttribArrayARB = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glGetVertexAttribfvARB");
        this.glGetVertexAttribfvARB = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetVertexAttribdvARB");
        this.glGetVertexAttribdvARB = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glGetVertexAttribivARB");
        this.glGetVertexAttribivARB = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glGetVertexAttribPointervARB");
        this.glGetVertexAttribPointervARB = functionAddress20;
        return b19 & functionAddress20 != 0L;
    }
    
    private boolean ARB_vertex_shader_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexAttrib1sARB");
        this.glVertexAttrib1sARB = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexAttrib1fARB");
        this.glVertexAttrib1fARB = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexAttrib1dARB");
        this.glVertexAttrib1dARB = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glVertexAttrib2sARB");
        this.glVertexAttrib2sARB = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glVertexAttrib2fARB");
        this.glVertexAttrib2fARB = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexAttrib2dARB");
        this.glVertexAttrib2dARB = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glVertexAttrib3sARB");
        this.glVertexAttrib3sARB = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glVertexAttrib3fARB");
        this.glVertexAttrib3fARB = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glVertexAttrib3dARB");
        this.glVertexAttrib3dARB = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVertexAttrib4sARB");
        this.glVertexAttrib4sARB = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glVertexAttrib4fARB");
        this.glVertexAttrib4fARB = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glVertexAttrib4dARB");
        this.glVertexAttrib4dARB = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glVertexAttrib4NubARB");
        this.glVertexAttrib4NubARB = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glVertexAttribPointerARB");
        this.glVertexAttribPointerARB = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glEnableVertexAttribArrayARB");
        this.glEnableVertexAttribArrayARB = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glDisableVertexAttribArrayARB");
        this.glDisableVertexAttribArrayARB = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glBindAttribLocationARB");
        this.glBindAttribLocationARB = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetActiveAttribARB");
        this.glGetActiveAttribARB = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glGetAttribLocationARB");
        this.glGetAttribLocationARB = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glGetVertexAttribfvARB");
        this.glGetVertexAttribfvARB = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glGetVertexAttribdvARB");
        this.glGetVertexAttribdvARB = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glGetVertexAttribivARB");
        this.glGetVertexAttribivARB = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glGetVertexAttribPointervARB");
        this.glGetVertexAttribPointervARB = functionAddress23;
        return b22 & functionAddress23 != 0L;
    }
    
    private boolean ARB_vertex_type_2_10_10_10_rev_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexP2ui");
        this.glVertexP2ui = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexP3ui");
        this.glVertexP3ui = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexP4ui");
        this.glVertexP4ui = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glVertexP2uiv");
        this.glVertexP2uiv = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glVertexP3uiv");
        this.glVertexP3uiv = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexP4uiv");
        this.glVertexP4uiv = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glTexCoordP1ui");
        this.glTexCoordP1ui = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glTexCoordP2ui");
        this.glTexCoordP2ui = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glTexCoordP3ui");
        this.glTexCoordP3ui = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glTexCoordP4ui");
        this.glTexCoordP4ui = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glTexCoordP1uiv");
        this.glTexCoordP1uiv = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glTexCoordP2uiv");
        this.glTexCoordP2uiv = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glTexCoordP3uiv");
        this.glTexCoordP3uiv = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glTexCoordP4uiv");
        this.glTexCoordP4uiv = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glMultiTexCoordP1ui");
        this.glMultiTexCoordP1ui = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glMultiTexCoordP2ui");
        this.glMultiTexCoordP2ui = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glMultiTexCoordP3ui");
        this.glMultiTexCoordP3ui = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glMultiTexCoordP4ui");
        this.glMultiTexCoordP4ui = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glMultiTexCoordP1uiv");
        this.glMultiTexCoordP1uiv = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glMultiTexCoordP2uiv");
        this.glMultiTexCoordP2uiv = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glMultiTexCoordP3uiv");
        this.glMultiTexCoordP3uiv = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glMultiTexCoordP4uiv");
        this.glMultiTexCoordP4uiv = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glNormalP3ui");
        this.glNormalP3ui = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glNormalP3uiv");
        this.glNormalP3uiv = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glColorP3ui");
        this.glColorP3ui = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glColorP4ui");
        this.glColorP4ui = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glColorP3uiv");
        this.glColorP3uiv = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glColorP4uiv");
        this.glColorP4uiv = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glSecondaryColorP3ui");
        this.glSecondaryColorP3ui = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glSecondaryColorP3uiv");
        this.glSecondaryColorP3uiv = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glVertexAttribP1ui");
        this.glVertexAttribP1ui = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glVertexAttribP2ui");
        this.glVertexAttribP2ui = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glVertexAttribP3ui");
        this.glVertexAttribP3ui = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glVertexAttribP4ui");
        this.glVertexAttribP4ui = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glVertexAttribP1uiv");
        this.glVertexAttribP1uiv = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glVertexAttribP2uiv");
        this.glVertexAttribP2uiv = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glVertexAttribP3uiv");
        this.glVertexAttribP3uiv = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glVertexAttribP4uiv");
        this.glVertexAttribP4uiv = functionAddress38;
        return b37 & functionAddress38 != 0L;
    }
    
    private boolean ARB_viewport_array_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glViewportArrayv");
        this.glViewportArrayv = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glViewportIndexedf");
        this.glViewportIndexedf = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glViewportIndexedfv");
        this.glViewportIndexedfv = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glScissorArrayv");
        this.glScissorArrayv = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glScissorIndexed");
        this.glScissorIndexed = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glScissorIndexedv");
        this.glScissorIndexedv = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glDepthRangeArrayv");
        this.glDepthRangeArrayv = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glDepthRangeIndexed");
        this.glDepthRangeIndexed = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetFloati_v");
        this.glGetFloati_v = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glGetDoublei_v");
        this.glGetDoublei_v = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGetIntegerIndexedvEXT");
        this.glGetIntegerIndexedvEXT = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glEnableIndexedEXT");
        this.glEnableIndexedEXT = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glDisableIndexedEXT");
        this.glDisableIndexedEXT = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glIsEnabledIndexedEXT");
        this.glIsEnabledIndexedEXT = functionAddress14;
        return b13 & functionAddress14 != 0L;
    }
    
    private boolean ARB_window_pos_initNativeFunctionAddresses(final boolean b) {
        boolean b2 = false;
        Label_0025: {
            if (!b) {
                final long functionAddress = GLContext.getFunctionAddress("glWindowPos2fARB");
                this.glWindowPos2fARB = functionAddress;
                if (functionAddress == 0L) {
                    b2 = false;
                    break Label_0025;
                }
            }
            b2 = true;
        }
        boolean b3 = false;
        Label_0050: {
            if (!b) {
                final long functionAddress2 = GLContext.getFunctionAddress("glWindowPos2dARB");
                this.glWindowPos2dARB = functionAddress2;
                if (functionAddress2 == 0L) {
                    b3 = false;
                    break Label_0050;
                }
            }
            b3 = true;
        }
        final boolean b4 = b2 & b3;
        boolean b5 = false;
        Label_0076: {
            if (!b) {
                final long functionAddress3 = GLContext.getFunctionAddress("glWindowPos2iARB");
                this.glWindowPos2iARB = functionAddress3;
                if (functionAddress3 == 0L) {
                    b5 = false;
                    break Label_0076;
                }
            }
            b5 = true;
        }
        final boolean b6 = b4 & b5;
        boolean b7 = false;
        Label_0102: {
            if (!b) {
                final long functionAddress4 = GLContext.getFunctionAddress("glWindowPos2sARB");
                this.glWindowPos2sARB = functionAddress4;
                if (functionAddress4 == 0L) {
                    b7 = false;
                    break Label_0102;
                }
            }
            b7 = true;
        }
        final boolean b8 = b6 & b7;
        boolean b9 = false;
        Label_0128: {
            if (!b) {
                final long functionAddress5 = GLContext.getFunctionAddress("glWindowPos3fARB");
                this.glWindowPos3fARB = functionAddress5;
                if (functionAddress5 == 0L) {
                    b9 = false;
                    break Label_0128;
                }
            }
            b9 = true;
        }
        final boolean b10 = b8 & b9;
        boolean b11 = false;
        Label_0154: {
            if (!b) {
                final long functionAddress6 = GLContext.getFunctionAddress("glWindowPos3dARB");
                this.glWindowPos3dARB = functionAddress6;
                if (functionAddress6 == 0L) {
                    b11 = false;
                    break Label_0154;
                }
            }
            b11 = true;
        }
        final boolean b12 = b10 & b11;
        boolean b13 = false;
        Label_0180: {
            if (!b) {
                final long functionAddress7 = GLContext.getFunctionAddress("glWindowPos3iARB");
                this.glWindowPos3iARB = functionAddress7;
                if (functionAddress7 == 0L) {
                    b13 = false;
                    break Label_0180;
                }
            }
            b13 = true;
        }
        final boolean b14 = b12 & b13;
        if (!b) {
            final long functionAddress8 = GLContext.getFunctionAddress("glWindowPos3sARB");
            this.glWindowPos3sARB = functionAddress8;
            if (functionAddress8 == 0L) {
                final boolean b15 = false;
                return b14 & b15;
            }
        }
        final boolean b15 = true;
        return b14 & b15;
    }
    
    private boolean ATI_draw_buffers_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawBuffersATI");
        this.glDrawBuffersATI = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean ATI_element_array_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glElementPointerATI");
        this.glElementPointerATI = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawElementArrayATI");
        this.glDrawElementArrayATI = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDrawRangeElementArrayATI");
        this.glDrawRangeElementArrayATI = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean ATI_envmap_bumpmap_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTexBumpParameterfvATI");
        this.glTexBumpParameterfvATI = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glTexBumpParameterivATI");
        this.glTexBumpParameterivATI = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetTexBumpParameterfvATI");
        this.glGetTexBumpParameterfvATI = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetTexBumpParameterivATI");
        this.glGetTexBumpParameterivATI = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean ATI_fragment_shader_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGenFragmentShadersATI");
        this.glGenFragmentShadersATI = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBindFragmentShaderATI");
        this.glBindFragmentShaderATI = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDeleteFragmentShaderATI");
        this.glDeleteFragmentShaderATI = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBeginFragmentShaderATI");
        this.glBeginFragmentShaderATI = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glEndFragmentShaderATI");
        this.glEndFragmentShaderATI = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glPassTexCoordATI");
        this.glPassTexCoordATI = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glSampleMapATI");
        this.glSampleMapATI = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glColorFragmentOp1ATI");
        this.glColorFragmentOp1ATI = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glColorFragmentOp2ATI");
        this.glColorFragmentOp2ATI = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glColorFragmentOp3ATI");
        this.glColorFragmentOp3ATI = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glAlphaFragmentOp1ATI");
        this.glAlphaFragmentOp1ATI = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glAlphaFragmentOp2ATI");
        this.glAlphaFragmentOp2ATI = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glAlphaFragmentOp3ATI");
        this.glAlphaFragmentOp3ATI = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glSetFragmentShaderConstantATI");
        this.glSetFragmentShaderConstantATI = functionAddress14;
        return b13 & functionAddress14 != 0L;
    }
    
    private boolean ATI_map_object_buffer_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMapObjectBufferATI");
        this.glMapObjectBufferATI = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glUnmapObjectBufferATI");
        this.glUnmapObjectBufferATI = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ATI_pn_triangles_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glPNTrianglesfATI");
        this.glPNTrianglesfATI = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glPNTrianglesiATI");
        this.glPNTrianglesiATI = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ATI_separate_stencil_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glStencilOpSeparateATI");
        this.glStencilOpSeparateATI = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glStencilFuncSeparateATI");
        this.glStencilFuncSeparateATI = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean ATI_vertex_array_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glNewObjectBufferATI");
        this.glNewObjectBufferATI = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glIsObjectBufferATI");
        this.glIsObjectBufferATI = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glUpdateObjectBufferATI");
        this.glUpdateObjectBufferATI = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetObjectBufferfvATI");
        this.glGetObjectBufferfvATI = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetObjectBufferivATI");
        this.glGetObjectBufferivATI = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glFreeObjectBufferATI");
        this.glFreeObjectBufferATI = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glArrayObjectATI");
        this.glArrayObjectATI = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetArrayObjectfvATI");
        this.glGetArrayObjectfvATI = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetArrayObjectivATI");
        this.glGetArrayObjectivATI = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVariantArrayObjectATI");
        this.glVariantArrayObjectATI = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGetVariantArrayObjectfvATI");
        this.glGetVariantArrayObjectfvATI = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glGetVariantArrayObjectivATI");
        this.glGetVariantArrayObjectivATI = functionAddress12;
        return b11 & functionAddress12 != 0L;
    }
    
    private boolean ATI_vertex_attrib_array_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexAttribArrayObjectATI");
        this.glVertexAttribArrayObjectATI = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetVertexAttribArrayObjectfvATI");
        this.glGetVertexAttribArrayObjectfvATI = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetVertexAttribArrayObjectivATI");
        this.glGetVertexAttribArrayObjectivATI = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean ATI_vertex_streams_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexStream2fATI");
        this.glVertexStream2fATI = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexStream2dATI");
        this.glVertexStream2dATI = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexStream2iATI");
        this.glVertexStream2iATI = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glVertexStream2sATI");
        this.glVertexStream2sATI = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glVertexStream3fATI");
        this.glVertexStream3fATI = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexStream3dATI");
        this.glVertexStream3dATI = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glVertexStream3iATI");
        this.glVertexStream3iATI = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glVertexStream3sATI");
        this.glVertexStream3sATI = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glVertexStream4fATI");
        this.glVertexStream4fATI = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVertexStream4dATI");
        this.glVertexStream4dATI = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glVertexStream4iATI");
        this.glVertexStream4iATI = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glVertexStream4sATI");
        this.glVertexStream4sATI = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glNormalStream3bATI");
        this.glNormalStream3bATI = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glNormalStream3fATI");
        this.glNormalStream3fATI = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glNormalStream3dATI");
        this.glNormalStream3dATI = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glNormalStream3iATI");
        this.glNormalStream3iATI = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glNormalStream3sATI");
        this.glNormalStream3sATI = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glClientActiveVertexStreamATI");
        this.glClientActiveVertexStreamATI = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glVertexBlendEnvfATI");
        this.glVertexBlendEnvfATI = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glVertexBlendEnviATI");
        this.glVertexBlendEnviATI = functionAddress20;
        return b19 & functionAddress20 != 0L;
    }
    
    private boolean EXT_bindable_uniform_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glUniformBufferEXT");
        this.glUniformBufferEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetUniformBufferSizeEXT");
        this.glGetUniformBufferSizeEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetUniformOffsetEXT");
        this.glGetUniformOffsetEXT = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean EXT_blend_color_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBlendColorEXT");
        this.glBlendColorEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_blend_equation_separate_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBlendEquationSeparateEXT");
        this.glBlendEquationSeparateEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_blend_func_separate_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBlendFuncSeparateEXT");
        this.glBlendFuncSeparateEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_blend_minmax_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBlendEquationEXT");
        this.glBlendEquationEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_compiled_vertex_array_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glLockArraysEXT");
        this.glLockArraysEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glUnlockArraysEXT");
        this.glUnlockArraysEXT = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean EXT_depth_bounds_test_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDepthBoundsEXT");
        this.glDepthBoundsEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_direct_state_access_initNativeFunctionAddresses(final boolean p0, final Set p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifne            20
        //     4: aload_0        
        //     5: ldc_w           "glClientAttribDefaultEXT"
        //     8: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //    11: dup2_x1        
        //    12: putfield        org/lwjgl/opengl/ContextCapabilities.glClientAttribDefaultEXT:J
        //    15: lconst_0       
        //    16: lcmp           
        //    17: ifeq            24
        //    20: iconst_1       
        //    21: goto            25
        //    24: iconst_0       
        //    25: iload_1        
        //    26: ifne            45
        //    29: aload_0        
        //    30: ldc_w           "glPushClientAttribDefaultEXT"
        //    33: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //    36: dup2_x1        
        //    37: putfield        org/lwjgl/opengl/ContextCapabilities.glPushClientAttribDefaultEXT:J
        //    40: lconst_0       
        //    41: lcmp           
        //    42: ifeq            49
        //    45: iconst_1       
        //    46: goto            50
        //    49: iconst_0       
        //    50: iand           
        //    51: iload_1        
        //    52: ifne            71
        //    55: aload_0        
        //    56: ldc_w           "glMatrixLoadfEXT"
        //    59: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //    62: dup2_x1        
        //    63: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixLoadfEXT:J
        //    66: lconst_0       
        //    67: lcmp           
        //    68: ifeq            75
        //    71: iconst_1       
        //    72: goto            76
        //    75: iconst_0       
        //    76: iand           
        //    77: iload_1        
        //    78: ifne            97
        //    81: aload_0        
        //    82: ldc_w           "glMatrixLoaddEXT"
        //    85: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //    88: dup2_x1        
        //    89: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixLoaddEXT:J
        //    92: lconst_0       
        //    93: lcmp           
        //    94: ifeq            101
        //    97: iconst_1       
        //    98: goto            102
        //   101: iconst_0       
        //   102: iand           
        //   103: iload_1        
        //   104: ifne            123
        //   107: aload_0        
        //   108: ldc_w           "glMatrixMultfEXT"
        //   111: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   114: dup2_x1        
        //   115: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixMultfEXT:J
        //   118: lconst_0       
        //   119: lcmp           
        //   120: ifeq            127
        //   123: iconst_1       
        //   124: goto            128
        //   127: iconst_0       
        //   128: iand           
        //   129: iload_1        
        //   130: ifne            149
        //   133: aload_0        
        //   134: ldc_w           "glMatrixMultdEXT"
        //   137: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   140: dup2_x1        
        //   141: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixMultdEXT:J
        //   144: lconst_0       
        //   145: lcmp           
        //   146: ifeq            153
        //   149: iconst_1       
        //   150: goto            154
        //   153: iconst_0       
        //   154: iand           
        //   155: iload_1        
        //   156: ifne            175
        //   159: aload_0        
        //   160: ldc_w           "glMatrixLoadIdentityEXT"
        //   163: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   166: dup2_x1        
        //   167: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixLoadIdentityEXT:J
        //   170: lconst_0       
        //   171: lcmp           
        //   172: ifeq            179
        //   175: iconst_1       
        //   176: goto            180
        //   179: iconst_0       
        //   180: iand           
        //   181: iload_1        
        //   182: ifne            201
        //   185: aload_0        
        //   186: ldc_w           "glMatrixRotatefEXT"
        //   189: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   192: dup2_x1        
        //   193: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixRotatefEXT:J
        //   196: lconst_0       
        //   197: lcmp           
        //   198: ifeq            205
        //   201: iconst_1       
        //   202: goto            206
        //   205: iconst_0       
        //   206: iand           
        //   207: iload_1        
        //   208: ifne            227
        //   211: aload_0        
        //   212: ldc_w           "glMatrixRotatedEXT"
        //   215: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   218: dup2_x1        
        //   219: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixRotatedEXT:J
        //   222: lconst_0       
        //   223: lcmp           
        //   224: ifeq            231
        //   227: iconst_1       
        //   228: goto            232
        //   231: iconst_0       
        //   232: iand           
        //   233: iload_1        
        //   234: ifne            253
        //   237: aload_0        
        //   238: ldc_w           "glMatrixScalefEXT"
        //   241: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   244: dup2_x1        
        //   245: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixScalefEXT:J
        //   248: lconst_0       
        //   249: lcmp           
        //   250: ifeq            257
        //   253: iconst_1       
        //   254: goto            258
        //   257: iconst_0       
        //   258: iand           
        //   259: iload_1        
        //   260: ifne            279
        //   263: aload_0        
        //   264: ldc_w           "glMatrixScaledEXT"
        //   267: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   270: dup2_x1        
        //   271: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixScaledEXT:J
        //   274: lconst_0       
        //   275: lcmp           
        //   276: ifeq            283
        //   279: iconst_1       
        //   280: goto            284
        //   283: iconst_0       
        //   284: iand           
        //   285: iload_1        
        //   286: ifne            305
        //   289: aload_0        
        //   290: ldc_w           "glMatrixTranslatefEXT"
        //   293: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   296: dup2_x1        
        //   297: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixTranslatefEXT:J
        //   300: lconst_0       
        //   301: lcmp           
        //   302: ifeq            309
        //   305: iconst_1       
        //   306: goto            310
        //   309: iconst_0       
        //   310: iand           
        //   311: iload_1        
        //   312: ifne            331
        //   315: aload_0        
        //   316: ldc_w           "glMatrixTranslatedEXT"
        //   319: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   322: dup2_x1        
        //   323: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixTranslatedEXT:J
        //   326: lconst_0       
        //   327: lcmp           
        //   328: ifeq            335
        //   331: iconst_1       
        //   332: goto            336
        //   335: iconst_0       
        //   336: iand           
        //   337: iload_1        
        //   338: ifne            357
        //   341: aload_0        
        //   342: ldc_w           "glMatrixOrthoEXT"
        //   345: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   348: dup2_x1        
        //   349: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixOrthoEXT:J
        //   352: lconst_0       
        //   353: lcmp           
        //   354: ifeq            361
        //   357: iconst_1       
        //   358: goto            362
        //   361: iconst_0       
        //   362: iand           
        //   363: iload_1        
        //   364: ifne            383
        //   367: aload_0        
        //   368: ldc_w           "glMatrixFrustumEXT"
        //   371: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   374: dup2_x1        
        //   375: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixFrustumEXT:J
        //   378: lconst_0       
        //   379: lcmp           
        //   380: ifeq            387
        //   383: iconst_1       
        //   384: goto            388
        //   387: iconst_0       
        //   388: iand           
        //   389: iload_1        
        //   390: ifne            409
        //   393: aload_0        
        //   394: ldc_w           "glMatrixPushEXT"
        //   397: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   400: dup2_x1        
        //   401: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixPushEXT:J
        //   404: lconst_0       
        //   405: lcmp           
        //   406: ifeq            413
        //   409: iconst_1       
        //   410: goto            414
        //   413: iconst_0       
        //   414: iand           
        //   415: iload_1        
        //   416: ifne            435
        //   419: aload_0        
        //   420: ldc_w           "glMatrixPopEXT"
        //   423: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   426: dup2_x1        
        //   427: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixPopEXT:J
        //   430: lconst_0       
        //   431: lcmp           
        //   432: ifeq            439
        //   435: iconst_1       
        //   436: goto            440
        //   439: iconst_0       
        //   440: iand           
        //   441: aload_0        
        //   442: ldc_w           "glTextureParameteriEXT"
        //   445: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   448: dup2_x1        
        //   449: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureParameteriEXT:J
        //   452: lconst_0       
        //   453: lcmp           
        //   454: ifeq            461
        //   457: iconst_1       
        //   458: goto            462
        //   461: iconst_0       
        //   462: iand           
        //   463: aload_0        
        //   464: ldc_w           "glTextureParameterivEXT"
        //   467: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   470: dup2_x1        
        //   471: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureParameterivEXT:J
        //   474: lconst_0       
        //   475: lcmp           
        //   476: ifeq            483
        //   479: iconst_1       
        //   480: goto            484
        //   483: iconst_0       
        //   484: iand           
        //   485: aload_0        
        //   486: ldc_w           "glTextureParameterfEXT"
        //   489: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   492: dup2_x1        
        //   493: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureParameterfEXT:J
        //   496: lconst_0       
        //   497: lcmp           
        //   498: ifeq            505
        //   501: iconst_1       
        //   502: goto            506
        //   505: iconst_0       
        //   506: iand           
        //   507: aload_0        
        //   508: ldc_w           "glTextureParameterfvEXT"
        //   511: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   514: dup2_x1        
        //   515: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureParameterfvEXT:J
        //   518: lconst_0       
        //   519: lcmp           
        //   520: ifeq            527
        //   523: iconst_1       
        //   524: goto            528
        //   527: iconst_0       
        //   528: iand           
        //   529: aload_0        
        //   530: ldc_w           "glTextureImage1DEXT"
        //   533: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   536: dup2_x1        
        //   537: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureImage1DEXT:J
        //   540: lconst_0       
        //   541: lcmp           
        //   542: ifeq            549
        //   545: iconst_1       
        //   546: goto            550
        //   549: iconst_0       
        //   550: iand           
        //   551: aload_0        
        //   552: ldc_w           "glTextureImage2DEXT"
        //   555: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   558: dup2_x1        
        //   559: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureImage2DEXT:J
        //   562: lconst_0       
        //   563: lcmp           
        //   564: ifeq            571
        //   567: iconst_1       
        //   568: goto            572
        //   571: iconst_0       
        //   572: iand           
        //   573: aload_0        
        //   574: ldc_w           "glTextureSubImage1DEXT"
        //   577: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   580: dup2_x1        
        //   581: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureSubImage1DEXT:J
        //   584: lconst_0       
        //   585: lcmp           
        //   586: ifeq            593
        //   589: iconst_1       
        //   590: goto            594
        //   593: iconst_0       
        //   594: iand           
        //   595: aload_0        
        //   596: ldc_w           "glTextureSubImage2DEXT"
        //   599: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   602: dup2_x1        
        //   603: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureSubImage2DEXT:J
        //   606: lconst_0       
        //   607: lcmp           
        //   608: ifeq            615
        //   611: iconst_1       
        //   612: goto            616
        //   615: iconst_0       
        //   616: iand           
        //   617: aload_0        
        //   618: ldc_w           "glCopyTextureImage1DEXT"
        //   621: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   624: dup2_x1        
        //   625: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyTextureImage1DEXT:J
        //   628: lconst_0       
        //   629: lcmp           
        //   630: ifeq            637
        //   633: iconst_1       
        //   634: goto            638
        //   637: iconst_0       
        //   638: iand           
        //   639: aload_0        
        //   640: ldc_w           "glCopyTextureImage2DEXT"
        //   643: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   646: dup2_x1        
        //   647: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyTextureImage2DEXT:J
        //   650: lconst_0       
        //   651: lcmp           
        //   652: ifeq            659
        //   655: iconst_1       
        //   656: goto            660
        //   659: iconst_0       
        //   660: iand           
        //   661: aload_0        
        //   662: ldc_w           "glCopyTextureSubImage1DEXT"
        //   665: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   668: dup2_x1        
        //   669: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyTextureSubImage1DEXT:J
        //   672: lconst_0       
        //   673: lcmp           
        //   674: ifeq            681
        //   677: iconst_1       
        //   678: goto            682
        //   681: iconst_0       
        //   682: iand           
        //   683: aload_0        
        //   684: ldc_w           "glCopyTextureSubImage2DEXT"
        //   687: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   690: dup2_x1        
        //   691: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyTextureSubImage2DEXT:J
        //   694: lconst_0       
        //   695: lcmp           
        //   696: ifeq            703
        //   699: iconst_1       
        //   700: goto            704
        //   703: iconst_0       
        //   704: iand           
        //   705: aload_0        
        //   706: ldc_w           "glGetTextureImageEXT"
        //   709: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   712: dup2_x1        
        //   713: putfield        org/lwjgl/opengl/ContextCapabilities.glGetTextureImageEXT:J
        //   716: lconst_0       
        //   717: lcmp           
        //   718: ifeq            725
        //   721: iconst_1       
        //   722: goto            726
        //   725: iconst_0       
        //   726: iand           
        //   727: aload_0        
        //   728: ldc_w           "glGetTextureParameterfvEXT"
        //   731: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   734: dup2_x1        
        //   735: putfield        org/lwjgl/opengl/ContextCapabilities.glGetTextureParameterfvEXT:J
        //   738: lconst_0       
        //   739: lcmp           
        //   740: ifeq            747
        //   743: iconst_1       
        //   744: goto            748
        //   747: iconst_0       
        //   748: iand           
        //   749: aload_0        
        //   750: ldc_w           "glGetTextureParameterivEXT"
        //   753: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   756: dup2_x1        
        //   757: putfield        org/lwjgl/opengl/ContextCapabilities.glGetTextureParameterivEXT:J
        //   760: lconst_0       
        //   761: lcmp           
        //   762: ifeq            769
        //   765: iconst_1       
        //   766: goto            770
        //   769: iconst_0       
        //   770: iand           
        //   771: aload_0        
        //   772: ldc_w           "glGetTextureLevelParameterfvEXT"
        //   775: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   778: dup2_x1        
        //   779: putfield        org/lwjgl/opengl/ContextCapabilities.glGetTextureLevelParameterfvEXT:J
        //   782: lconst_0       
        //   783: lcmp           
        //   784: ifeq            791
        //   787: iconst_1       
        //   788: goto            792
        //   791: iconst_0       
        //   792: iand           
        //   793: aload_0        
        //   794: ldc_w           "glGetTextureLevelParameterivEXT"
        //   797: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   800: dup2_x1        
        //   801: putfield        org/lwjgl/opengl/ContextCapabilities.glGetTextureLevelParameterivEXT:J
        //   804: lconst_0       
        //   805: lcmp           
        //   806: ifeq            813
        //   809: iconst_1       
        //   810: goto            814
        //   813: iconst_0       
        //   814: iand           
        //   815: aload_2        
        //   816: ldc_w           "OpenGL12"
        //   819: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //   824: ifeq            843
        //   827: aload_0        
        //   828: ldc_w           "glTextureImage3DEXT"
        //   831: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   834: dup2_x1        
        //   835: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureImage3DEXT:J
        //   838: lconst_0       
        //   839: lcmp           
        //   840: ifeq            847
        //   843: iconst_1       
        //   844: goto            848
        //   847: iconst_0       
        //   848: iand           
        //   849: aload_2        
        //   850: ldc_w           "OpenGL12"
        //   853: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //   858: ifeq            877
        //   861: aload_0        
        //   862: ldc_w           "glTextureSubImage3DEXT"
        //   865: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   868: dup2_x1        
        //   869: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureSubImage3DEXT:J
        //   872: lconst_0       
        //   873: lcmp           
        //   874: ifeq            881
        //   877: iconst_1       
        //   878: goto            882
        //   881: iconst_0       
        //   882: iand           
        //   883: aload_2        
        //   884: ldc_w           "OpenGL12"
        //   887: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //   892: ifeq            911
        //   895: aload_0        
        //   896: ldc_w           "glCopyTextureSubImage3DEXT"
        //   899: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   902: dup2_x1        
        //   903: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyTextureSubImage3DEXT:J
        //   906: lconst_0       
        //   907: lcmp           
        //   908: ifeq            915
        //   911: iconst_1       
        //   912: goto            916
        //   915: iconst_0       
        //   916: iand           
        //   917: aload_2        
        //   918: ldc_w           "OpenGL13"
        //   921: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //   926: ifeq            945
        //   929: aload_0        
        //   930: ldc_w           "glBindMultiTextureEXT"
        //   933: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   936: dup2_x1        
        //   937: putfield        org/lwjgl/opengl/ContextCapabilities.glBindMultiTextureEXT:J
        //   940: lconst_0       
        //   941: lcmp           
        //   942: ifeq            949
        //   945: iconst_1       
        //   946: goto            950
        //   949: iconst_0       
        //   950: iand           
        //   951: iload_1        
        //   952: ifne            983
        //   955: aload_2        
        //   956: ldc_w           "OpenGL13"
        //   959: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //   964: ifeq            983
        //   967: aload_0        
        //   968: ldc_w           "glMultiTexCoordPointerEXT"
        //   971: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //   974: dup2_x1        
        //   975: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexCoordPointerEXT:J
        //   978: lconst_0       
        //   979: lcmp           
        //   980: ifeq            987
        //   983: iconst_1       
        //   984: goto            988
        //   987: iconst_0       
        //   988: iand           
        //   989: iload_1        
        //   990: ifne            1021
        //   993: aload_2        
        //   994: ldc_w           "OpenGL13"
        //   997: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1002: ifeq            1021
        //  1005: aload_0        
        //  1006: ldc_w           "glMultiTexEnvfEXT"
        //  1009: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1012: dup2_x1        
        //  1013: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexEnvfEXT:J
        //  1016: lconst_0       
        //  1017: lcmp           
        //  1018: ifeq            1025
        //  1021: iconst_1       
        //  1022: goto            1026
        //  1025: iconst_0       
        //  1026: iand           
        //  1027: iload_1        
        //  1028: ifne            1059
        //  1031: aload_2        
        //  1032: ldc_w           "OpenGL13"
        //  1035: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1040: ifeq            1059
        //  1043: aload_0        
        //  1044: ldc_w           "glMultiTexEnvfvEXT"
        //  1047: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1050: dup2_x1        
        //  1051: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexEnvfvEXT:J
        //  1054: lconst_0       
        //  1055: lcmp           
        //  1056: ifeq            1063
        //  1059: iconst_1       
        //  1060: goto            1064
        //  1063: iconst_0       
        //  1064: iand           
        //  1065: iload_1        
        //  1066: ifne            1097
        //  1069: aload_2        
        //  1070: ldc_w           "OpenGL13"
        //  1073: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1078: ifeq            1097
        //  1081: aload_0        
        //  1082: ldc_w           "glMultiTexEnviEXT"
        //  1085: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1088: dup2_x1        
        //  1089: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexEnviEXT:J
        //  1092: lconst_0       
        //  1093: lcmp           
        //  1094: ifeq            1101
        //  1097: iconst_1       
        //  1098: goto            1102
        //  1101: iconst_0       
        //  1102: iand           
        //  1103: iload_1        
        //  1104: ifne            1135
        //  1107: aload_2        
        //  1108: ldc_w           "OpenGL13"
        //  1111: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1116: ifeq            1135
        //  1119: aload_0        
        //  1120: ldc_w           "glMultiTexEnvivEXT"
        //  1123: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1126: dup2_x1        
        //  1127: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexEnvivEXT:J
        //  1130: lconst_0       
        //  1131: lcmp           
        //  1132: ifeq            1139
        //  1135: iconst_1       
        //  1136: goto            1140
        //  1139: iconst_0       
        //  1140: iand           
        //  1141: iload_1        
        //  1142: ifne            1173
        //  1145: aload_2        
        //  1146: ldc_w           "OpenGL13"
        //  1149: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1154: ifeq            1173
        //  1157: aload_0        
        //  1158: ldc_w           "glMultiTexGendEXT"
        //  1161: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1164: dup2_x1        
        //  1165: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexGendEXT:J
        //  1168: lconst_0       
        //  1169: lcmp           
        //  1170: ifeq            1177
        //  1173: iconst_1       
        //  1174: goto            1178
        //  1177: iconst_0       
        //  1178: iand           
        //  1179: iload_1        
        //  1180: ifne            1211
        //  1183: aload_2        
        //  1184: ldc_w           "OpenGL13"
        //  1187: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1192: ifeq            1211
        //  1195: aload_0        
        //  1196: ldc_w           "glMultiTexGendvEXT"
        //  1199: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1202: dup2_x1        
        //  1203: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexGendvEXT:J
        //  1206: lconst_0       
        //  1207: lcmp           
        //  1208: ifeq            1215
        //  1211: iconst_1       
        //  1212: goto            1216
        //  1215: iconst_0       
        //  1216: iand           
        //  1217: iload_1        
        //  1218: ifne            1249
        //  1221: aload_2        
        //  1222: ldc_w           "OpenGL13"
        //  1225: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1230: ifeq            1249
        //  1233: aload_0        
        //  1234: ldc_w           "glMultiTexGenfEXT"
        //  1237: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1240: dup2_x1        
        //  1241: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexGenfEXT:J
        //  1244: lconst_0       
        //  1245: lcmp           
        //  1246: ifeq            1253
        //  1249: iconst_1       
        //  1250: goto            1254
        //  1253: iconst_0       
        //  1254: iand           
        //  1255: iload_1        
        //  1256: ifne            1287
        //  1259: aload_2        
        //  1260: ldc_w           "OpenGL13"
        //  1263: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1268: ifeq            1287
        //  1271: aload_0        
        //  1272: ldc_w           "glMultiTexGenfvEXT"
        //  1275: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1278: dup2_x1        
        //  1279: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexGenfvEXT:J
        //  1282: lconst_0       
        //  1283: lcmp           
        //  1284: ifeq            1291
        //  1287: iconst_1       
        //  1288: goto            1292
        //  1291: iconst_0       
        //  1292: iand           
        //  1293: iload_1        
        //  1294: ifne            1325
        //  1297: aload_2        
        //  1298: ldc_w           "OpenGL13"
        //  1301: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1306: ifeq            1325
        //  1309: aload_0        
        //  1310: ldc_w           "glMultiTexGeniEXT"
        //  1313: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1316: dup2_x1        
        //  1317: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexGeniEXT:J
        //  1320: lconst_0       
        //  1321: lcmp           
        //  1322: ifeq            1329
        //  1325: iconst_1       
        //  1326: goto            1330
        //  1329: iconst_0       
        //  1330: iand           
        //  1331: iload_1        
        //  1332: ifne            1363
        //  1335: aload_2        
        //  1336: ldc_w           "OpenGL13"
        //  1339: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1344: ifeq            1363
        //  1347: aload_0        
        //  1348: ldc_w           "glMultiTexGenivEXT"
        //  1351: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1354: dup2_x1        
        //  1355: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexGenivEXT:J
        //  1358: lconst_0       
        //  1359: lcmp           
        //  1360: ifeq            1367
        //  1363: iconst_1       
        //  1364: goto            1368
        //  1367: iconst_0       
        //  1368: iand           
        //  1369: iload_1        
        //  1370: ifne            1401
        //  1373: aload_2        
        //  1374: ldc_w           "OpenGL13"
        //  1377: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1382: ifeq            1401
        //  1385: aload_0        
        //  1386: ldc_w           "glGetMultiTexEnvfvEXT"
        //  1389: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1392: dup2_x1        
        //  1393: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexEnvfvEXT:J
        //  1396: lconst_0       
        //  1397: lcmp           
        //  1398: ifeq            1405
        //  1401: iconst_1       
        //  1402: goto            1406
        //  1405: iconst_0       
        //  1406: iand           
        //  1407: iload_1        
        //  1408: ifne            1439
        //  1411: aload_2        
        //  1412: ldc_w           "OpenGL13"
        //  1415: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1420: ifeq            1439
        //  1423: aload_0        
        //  1424: ldc_w           "glGetMultiTexEnvivEXT"
        //  1427: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1430: dup2_x1        
        //  1431: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexEnvivEXT:J
        //  1434: lconst_0       
        //  1435: lcmp           
        //  1436: ifeq            1443
        //  1439: iconst_1       
        //  1440: goto            1444
        //  1443: iconst_0       
        //  1444: iand           
        //  1445: iload_1        
        //  1446: ifne            1477
        //  1449: aload_2        
        //  1450: ldc_w           "OpenGL13"
        //  1453: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1458: ifeq            1477
        //  1461: aload_0        
        //  1462: ldc_w           "glGetMultiTexGendvEXT"
        //  1465: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1468: dup2_x1        
        //  1469: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexGendvEXT:J
        //  1472: lconst_0       
        //  1473: lcmp           
        //  1474: ifeq            1481
        //  1477: iconst_1       
        //  1478: goto            1482
        //  1481: iconst_0       
        //  1482: iand           
        //  1483: iload_1        
        //  1484: ifne            1515
        //  1487: aload_2        
        //  1488: ldc_w           "OpenGL13"
        //  1491: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1496: ifeq            1515
        //  1499: aload_0        
        //  1500: ldc_w           "glGetMultiTexGenfvEXT"
        //  1503: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1506: dup2_x1        
        //  1507: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexGenfvEXT:J
        //  1510: lconst_0       
        //  1511: lcmp           
        //  1512: ifeq            1519
        //  1515: iconst_1       
        //  1516: goto            1520
        //  1519: iconst_0       
        //  1520: iand           
        //  1521: iload_1        
        //  1522: ifne            1553
        //  1525: aload_2        
        //  1526: ldc_w           "OpenGL13"
        //  1529: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1534: ifeq            1553
        //  1537: aload_0        
        //  1538: ldc_w           "glGetMultiTexGenivEXT"
        //  1541: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1544: dup2_x1        
        //  1545: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexGenivEXT:J
        //  1548: lconst_0       
        //  1549: lcmp           
        //  1550: ifeq            1557
        //  1553: iconst_1       
        //  1554: goto            1558
        //  1557: iconst_0       
        //  1558: iand           
        //  1559: aload_2        
        //  1560: ldc_w           "OpenGL13"
        //  1563: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1568: ifeq            1587
        //  1571: aload_0        
        //  1572: ldc_w           "glMultiTexParameteriEXT"
        //  1575: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1578: dup2_x1        
        //  1579: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexParameteriEXT:J
        //  1582: lconst_0       
        //  1583: lcmp           
        //  1584: ifeq            1591
        //  1587: iconst_1       
        //  1588: goto            1592
        //  1591: iconst_0       
        //  1592: iand           
        //  1593: aload_2        
        //  1594: ldc_w           "OpenGL13"
        //  1597: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1602: ifeq            1621
        //  1605: aload_0        
        //  1606: ldc_w           "glMultiTexParameterivEXT"
        //  1609: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1612: dup2_x1        
        //  1613: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexParameterivEXT:J
        //  1616: lconst_0       
        //  1617: lcmp           
        //  1618: ifeq            1625
        //  1621: iconst_1       
        //  1622: goto            1626
        //  1625: iconst_0       
        //  1626: iand           
        //  1627: aload_2        
        //  1628: ldc_w           "OpenGL13"
        //  1631: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1636: ifeq            1655
        //  1639: aload_0        
        //  1640: ldc_w           "glMultiTexParameterfEXT"
        //  1643: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1646: dup2_x1        
        //  1647: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexParameterfEXT:J
        //  1650: lconst_0       
        //  1651: lcmp           
        //  1652: ifeq            1659
        //  1655: iconst_1       
        //  1656: goto            1660
        //  1659: iconst_0       
        //  1660: iand           
        //  1661: aload_2        
        //  1662: ldc_w           "OpenGL13"
        //  1665: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1670: ifeq            1689
        //  1673: aload_0        
        //  1674: ldc_w           "glMultiTexParameterfvEXT"
        //  1677: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1680: dup2_x1        
        //  1681: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexParameterfvEXT:J
        //  1684: lconst_0       
        //  1685: lcmp           
        //  1686: ifeq            1693
        //  1689: iconst_1       
        //  1690: goto            1694
        //  1693: iconst_0       
        //  1694: iand           
        //  1695: aload_2        
        //  1696: ldc_w           "OpenGL13"
        //  1699: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1704: ifeq            1723
        //  1707: aload_0        
        //  1708: ldc_w           "glMultiTexImage1DEXT"
        //  1711: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1714: dup2_x1        
        //  1715: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexImage1DEXT:J
        //  1718: lconst_0       
        //  1719: lcmp           
        //  1720: ifeq            1727
        //  1723: iconst_1       
        //  1724: goto            1728
        //  1727: iconst_0       
        //  1728: iand           
        //  1729: aload_2        
        //  1730: ldc_w           "OpenGL13"
        //  1733: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1738: ifeq            1757
        //  1741: aload_0        
        //  1742: ldc_w           "glMultiTexImage2DEXT"
        //  1745: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1748: dup2_x1        
        //  1749: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexImage2DEXT:J
        //  1752: lconst_0       
        //  1753: lcmp           
        //  1754: ifeq            1761
        //  1757: iconst_1       
        //  1758: goto            1762
        //  1761: iconst_0       
        //  1762: iand           
        //  1763: aload_2        
        //  1764: ldc_w           "OpenGL13"
        //  1767: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1772: ifeq            1791
        //  1775: aload_0        
        //  1776: ldc_w           "glMultiTexSubImage1DEXT"
        //  1779: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1782: dup2_x1        
        //  1783: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexSubImage1DEXT:J
        //  1786: lconst_0       
        //  1787: lcmp           
        //  1788: ifeq            1795
        //  1791: iconst_1       
        //  1792: goto            1796
        //  1795: iconst_0       
        //  1796: iand           
        //  1797: aload_2        
        //  1798: ldc_w           "OpenGL13"
        //  1801: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1806: ifeq            1825
        //  1809: aload_0        
        //  1810: ldc_w           "glMultiTexSubImage2DEXT"
        //  1813: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1816: dup2_x1        
        //  1817: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexSubImage2DEXT:J
        //  1820: lconst_0       
        //  1821: lcmp           
        //  1822: ifeq            1829
        //  1825: iconst_1       
        //  1826: goto            1830
        //  1829: iconst_0       
        //  1830: iand           
        //  1831: aload_2        
        //  1832: ldc_w           "OpenGL13"
        //  1835: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1840: ifeq            1859
        //  1843: aload_0        
        //  1844: ldc_w           "glCopyMultiTexImage1DEXT"
        //  1847: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1850: dup2_x1        
        //  1851: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyMultiTexImage1DEXT:J
        //  1854: lconst_0       
        //  1855: lcmp           
        //  1856: ifeq            1863
        //  1859: iconst_1       
        //  1860: goto            1864
        //  1863: iconst_0       
        //  1864: iand           
        //  1865: aload_2        
        //  1866: ldc_w           "OpenGL13"
        //  1869: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1874: ifeq            1893
        //  1877: aload_0        
        //  1878: ldc_w           "glCopyMultiTexImage2DEXT"
        //  1881: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1884: dup2_x1        
        //  1885: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyMultiTexImage2DEXT:J
        //  1888: lconst_0       
        //  1889: lcmp           
        //  1890: ifeq            1897
        //  1893: iconst_1       
        //  1894: goto            1898
        //  1897: iconst_0       
        //  1898: iand           
        //  1899: aload_2        
        //  1900: ldc_w           "OpenGL13"
        //  1903: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1908: ifeq            1927
        //  1911: aload_0        
        //  1912: ldc_w           "glCopyMultiTexSubImage1DEXT"
        //  1915: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1918: dup2_x1        
        //  1919: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyMultiTexSubImage1DEXT:J
        //  1922: lconst_0       
        //  1923: lcmp           
        //  1924: ifeq            1931
        //  1927: iconst_1       
        //  1928: goto            1932
        //  1931: iconst_0       
        //  1932: iand           
        //  1933: aload_2        
        //  1934: ldc_w           "OpenGL13"
        //  1937: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1942: ifeq            1961
        //  1945: aload_0        
        //  1946: ldc_w           "glCopyMultiTexSubImage2DEXT"
        //  1949: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1952: dup2_x1        
        //  1953: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyMultiTexSubImage2DEXT:J
        //  1956: lconst_0       
        //  1957: lcmp           
        //  1958: ifeq            1965
        //  1961: iconst_1       
        //  1962: goto            1966
        //  1965: iconst_0       
        //  1966: iand           
        //  1967: aload_2        
        //  1968: ldc_w           "OpenGL13"
        //  1971: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  1976: ifeq            1995
        //  1979: aload_0        
        //  1980: ldc_w           "glGetMultiTexImageEXT"
        //  1983: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  1986: dup2_x1        
        //  1987: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexImageEXT:J
        //  1990: lconst_0       
        //  1991: lcmp           
        //  1992: ifeq            1999
        //  1995: iconst_1       
        //  1996: goto            2000
        //  1999: iconst_0       
        //  2000: iand           
        //  2001: aload_2        
        //  2002: ldc_w           "OpenGL13"
        //  2005: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2010: ifeq            2029
        //  2013: aload_0        
        //  2014: ldc_w           "glGetMultiTexParameterfvEXT"
        //  2017: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2020: dup2_x1        
        //  2021: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexParameterfvEXT:J
        //  2024: lconst_0       
        //  2025: lcmp           
        //  2026: ifeq            2033
        //  2029: iconst_1       
        //  2030: goto            2034
        //  2033: iconst_0       
        //  2034: iand           
        //  2035: aload_2        
        //  2036: ldc_w           "OpenGL13"
        //  2039: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2044: ifeq            2063
        //  2047: aload_0        
        //  2048: ldc_w           "glGetMultiTexParameterivEXT"
        //  2051: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2054: dup2_x1        
        //  2055: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexParameterivEXT:J
        //  2058: lconst_0       
        //  2059: lcmp           
        //  2060: ifeq            2067
        //  2063: iconst_1       
        //  2064: goto            2068
        //  2067: iconst_0       
        //  2068: iand           
        //  2069: aload_2        
        //  2070: ldc_w           "OpenGL13"
        //  2073: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2078: ifeq            2097
        //  2081: aload_0        
        //  2082: ldc_w           "glGetMultiTexLevelParameterfvEXT"
        //  2085: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2088: dup2_x1        
        //  2089: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexLevelParameterfvEXT:J
        //  2092: lconst_0       
        //  2093: lcmp           
        //  2094: ifeq            2101
        //  2097: iconst_1       
        //  2098: goto            2102
        //  2101: iconst_0       
        //  2102: iand           
        //  2103: aload_2        
        //  2104: ldc_w           "OpenGL13"
        //  2107: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2112: ifeq            2131
        //  2115: aload_0        
        //  2116: ldc_w           "glGetMultiTexLevelParameterivEXT"
        //  2119: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2122: dup2_x1        
        //  2123: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexLevelParameterivEXT:J
        //  2126: lconst_0       
        //  2127: lcmp           
        //  2128: ifeq            2135
        //  2131: iconst_1       
        //  2132: goto            2136
        //  2135: iconst_0       
        //  2136: iand           
        //  2137: aload_2        
        //  2138: ldc_w           "OpenGL13"
        //  2141: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2146: ifeq            2165
        //  2149: aload_0        
        //  2150: ldc_w           "glMultiTexImage3DEXT"
        //  2153: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2156: dup2_x1        
        //  2157: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexImage3DEXT:J
        //  2160: lconst_0       
        //  2161: lcmp           
        //  2162: ifeq            2169
        //  2165: iconst_1       
        //  2166: goto            2170
        //  2169: iconst_0       
        //  2170: iand           
        //  2171: aload_2        
        //  2172: ldc_w           "OpenGL13"
        //  2175: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2180: ifeq            2199
        //  2183: aload_0        
        //  2184: ldc_w           "glMultiTexSubImage3DEXT"
        //  2187: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2190: dup2_x1        
        //  2191: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexSubImage3DEXT:J
        //  2194: lconst_0       
        //  2195: lcmp           
        //  2196: ifeq            2203
        //  2199: iconst_1       
        //  2200: goto            2204
        //  2203: iconst_0       
        //  2204: iand           
        //  2205: aload_2        
        //  2206: ldc_w           "OpenGL13"
        //  2209: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2214: ifeq            2233
        //  2217: aload_0        
        //  2218: ldc_w           "glCopyMultiTexSubImage3DEXT"
        //  2221: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2224: dup2_x1        
        //  2225: putfield        org/lwjgl/opengl/ContextCapabilities.glCopyMultiTexSubImage3DEXT:J
        //  2228: lconst_0       
        //  2229: lcmp           
        //  2230: ifeq            2237
        //  2233: iconst_1       
        //  2234: goto            2238
        //  2237: iconst_0       
        //  2238: iand           
        //  2239: iload_1        
        //  2240: ifne            2271
        //  2243: aload_2        
        //  2244: ldc_w           "OpenGL13"
        //  2247: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2252: ifeq            2271
        //  2255: aload_0        
        //  2256: ldc_w           "glEnableClientStateIndexedEXT"
        //  2259: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2262: dup2_x1        
        //  2263: putfield        org/lwjgl/opengl/ContextCapabilities.glEnableClientStateIndexedEXT:J
        //  2266: lconst_0       
        //  2267: lcmp           
        //  2268: ifeq            2275
        //  2271: iconst_1       
        //  2272: goto            2276
        //  2275: iconst_0       
        //  2276: iand           
        //  2277: iload_1        
        //  2278: ifne            2309
        //  2281: aload_2        
        //  2282: ldc_w           "OpenGL13"
        //  2285: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2290: ifeq            2309
        //  2293: aload_0        
        //  2294: ldc_w           "glDisableClientStateIndexedEXT"
        //  2297: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2300: dup2_x1        
        //  2301: putfield        org/lwjgl/opengl/ContextCapabilities.glDisableClientStateIndexedEXT:J
        //  2304: lconst_0       
        //  2305: lcmp           
        //  2306: ifeq            2313
        //  2309: iconst_1       
        //  2310: goto            2314
        //  2313: iconst_0       
        //  2314: iand           
        //  2315: aload_2        
        //  2316: ldc_w           "OpenGL30"
        //  2319: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2324: ifeq            2340
        //  2327: aload_0        
        //  2328: ldc_w           "glEnableClientStateiEXT"
        //  2331: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2334: dup2_x1        
        //  2335: putfield        org/lwjgl/opengl/ContextCapabilities.glEnableClientStateiEXT:J
        //  2338: lconst_0       
        //  2339: lcmp           
        //  2340: iconst_1       
        //  2341: iand           
        //  2342: aload_2        
        //  2343: ldc_w           "OpenGL30"
        //  2346: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2351: ifeq            2367
        //  2354: aload_0        
        //  2355: ldc_w           "glDisableClientStateiEXT"
        //  2358: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2361: dup2_x1        
        //  2362: putfield        org/lwjgl/opengl/ContextCapabilities.glDisableClientStateiEXT:J
        //  2365: lconst_0       
        //  2366: lcmp           
        //  2367: iconst_1       
        //  2368: iand           
        //  2369: aload_2        
        //  2370: ldc_w           "OpenGL13"
        //  2373: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2378: ifeq            2397
        //  2381: aload_0        
        //  2382: ldc_w           "glGetFloatIndexedvEXT"
        //  2385: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2388: dup2_x1        
        //  2389: putfield        org/lwjgl/opengl/ContextCapabilities.glGetFloatIndexedvEXT:J
        //  2392: lconst_0       
        //  2393: lcmp           
        //  2394: ifeq            2401
        //  2397: iconst_1       
        //  2398: goto            2402
        //  2401: iconst_0       
        //  2402: iand           
        //  2403: aload_2        
        //  2404: ldc_w           "OpenGL13"
        //  2407: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2412: ifeq            2431
        //  2415: aload_0        
        //  2416: ldc_w           "glGetDoubleIndexedvEXT"
        //  2419: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2422: dup2_x1        
        //  2423: putfield        org/lwjgl/opengl/ContextCapabilities.glGetDoubleIndexedvEXT:J
        //  2426: lconst_0       
        //  2427: lcmp           
        //  2428: ifeq            2435
        //  2431: iconst_1       
        //  2432: goto            2436
        //  2435: iconst_0       
        //  2436: iand           
        //  2437: aload_2        
        //  2438: ldc_w           "OpenGL13"
        //  2441: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2446: ifeq            2465
        //  2449: aload_0        
        //  2450: ldc_w           "glGetPointerIndexedvEXT"
        //  2453: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2456: dup2_x1        
        //  2457: putfield        org/lwjgl/opengl/ContextCapabilities.glGetPointerIndexedvEXT:J
        //  2460: lconst_0       
        //  2461: lcmp           
        //  2462: ifeq            2469
        //  2465: iconst_1       
        //  2466: goto            2470
        //  2469: iconst_0       
        //  2470: iand           
        //  2471: aload_2        
        //  2472: ldc_w           "OpenGL30"
        //  2475: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2480: ifeq            2496
        //  2483: aload_0        
        //  2484: ldc_w           "glGetFloati_vEXT"
        //  2487: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2490: dup2_x1        
        //  2491: putfield        org/lwjgl/opengl/ContextCapabilities.glGetFloati_vEXT:J
        //  2494: lconst_0       
        //  2495: lcmp           
        //  2496: iconst_1       
        //  2497: iand           
        //  2498: aload_2        
        //  2499: ldc_w           "OpenGL30"
        //  2502: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2507: ifeq            2523
        //  2510: aload_0        
        //  2511: ldc_w           "glGetDoublei_vEXT"
        //  2514: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2517: dup2_x1        
        //  2518: putfield        org/lwjgl/opengl/ContextCapabilities.glGetDoublei_vEXT:J
        //  2521: lconst_0       
        //  2522: lcmp           
        //  2523: iconst_1       
        //  2524: iand           
        //  2525: aload_2        
        //  2526: ldc_w           "OpenGL30"
        //  2529: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2534: ifeq            2550
        //  2537: aload_0        
        //  2538: ldc_w           "glGetPointeri_vEXT"
        //  2541: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2544: dup2_x1        
        //  2545: putfield        org/lwjgl/opengl/ContextCapabilities.glGetPointeri_vEXT:J
        //  2548: lconst_0       
        //  2549: lcmp           
        //  2550: iconst_1       
        //  2551: iand           
        //  2552: aload_2        
        //  2553: ldc_w           "OpenGL13"
        //  2556: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2561: ifeq            2580
        //  2564: aload_0        
        //  2565: ldc_w           "glEnableIndexedEXT"
        //  2568: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2571: dup2_x1        
        //  2572: putfield        org/lwjgl/opengl/ContextCapabilities.glEnableIndexedEXT:J
        //  2575: lconst_0       
        //  2576: lcmp           
        //  2577: ifeq            2584
        //  2580: iconst_1       
        //  2581: goto            2585
        //  2584: iconst_0       
        //  2585: iand           
        //  2586: aload_2        
        //  2587: ldc_w           "OpenGL13"
        //  2590: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2595: ifeq            2614
        //  2598: aload_0        
        //  2599: ldc_w           "glDisableIndexedEXT"
        //  2602: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2605: dup2_x1        
        //  2606: putfield        org/lwjgl/opengl/ContextCapabilities.glDisableIndexedEXT:J
        //  2609: lconst_0       
        //  2610: lcmp           
        //  2611: ifeq            2618
        //  2614: iconst_1       
        //  2615: goto            2619
        //  2618: iconst_0       
        //  2619: iand           
        //  2620: aload_2        
        //  2621: ldc_w           "OpenGL13"
        //  2624: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2629: ifeq            2648
        //  2632: aload_0        
        //  2633: ldc_w           "glIsEnabledIndexedEXT"
        //  2636: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2639: dup2_x1        
        //  2640: putfield        org/lwjgl/opengl/ContextCapabilities.glIsEnabledIndexedEXT:J
        //  2643: lconst_0       
        //  2644: lcmp           
        //  2645: ifeq            2652
        //  2648: iconst_1       
        //  2649: goto            2653
        //  2652: iconst_0       
        //  2653: iand           
        //  2654: aload_2        
        //  2655: ldc_w           "OpenGL13"
        //  2658: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2663: ifeq            2682
        //  2666: aload_0        
        //  2667: ldc_w           "glGetIntegerIndexedvEXT"
        //  2670: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2673: dup2_x1        
        //  2674: putfield        org/lwjgl/opengl/ContextCapabilities.glGetIntegerIndexedvEXT:J
        //  2677: lconst_0       
        //  2678: lcmp           
        //  2679: ifeq            2686
        //  2682: iconst_1       
        //  2683: goto            2687
        //  2686: iconst_0       
        //  2687: iand           
        //  2688: aload_2        
        //  2689: ldc_w           "OpenGL13"
        //  2692: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2697: ifeq            2716
        //  2700: aload_0        
        //  2701: ldc_w           "glGetBooleanIndexedvEXT"
        //  2704: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2707: dup2_x1        
        //  2708: putfield        org/lwjgl/opengl/ContextCapabilities.glGetBooleanIndexedvEXT:J
        //  2711: lconst_0       
        //  2712: lcmp           
        //  2713: ifeq            2720
        //  2716: iconst_1       
        //  2717: goto            2721
        //  2720: iconst_0       
        //  2721: iand           
        //  2722: aload_2        
        //  2723: ldc_w           "GL_ARB_vertex_program"
        //  2726: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2731: ifeq            2750
        //  2734: aload_0        
        //  2735: ldc_w           "glNamedProgramStringEXT"
        //  2738: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2741: dup2_x1        
        //  2742: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramStringEXT:J
        //  2745: lconst_0       
        //  2746: lcmp           
        //  2747: ifeq            2754
        //  2750: iconst_1       
        //  2751: goto            2755
        //  2754: iconst_0       
        //  2755: iand           
        //  2756: aload_2        
        //  2757: ldc_w           "GL_ARB_vertex_program"
        //  2760: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2765: ifeq            2784
        //  2768: aload_0        
        //  2769: ldc_w           "glNamedProgramLocalParameter4dEXT"
        //  2772: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2775: dup2_x1        
        //  2776: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParameter4dEXT:J
        //  2779: lconst_0       
        //  2780: lcmp           
        //  2781: ifeq            2788
        //  2784: iconst_1       
        //  2785: goto            2789
        //  2788: iconst_0       
        //  2789: iand           
        //  2790: aload_2        
        //  2791: ldc_w           "GL_ARB_vertex_program"
        //  2794: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2799: ifeq            2818
        //  2802: aload_0        
        //  2803: ldc_w           "glNamedProgramLocalParameter4dvEXT"
        //  2806: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2809: dup2_x1        
        //  2810: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParameter4dvEXT:J
        //  2813: lconst_0       
        //  2814: lcmp           
        //  2815: ifeq            2822
        //  2818: iconst_1       
        //  2819: goto            2823
        //  2822: iconst_0       
        //  2823: iand           
        //  2824: aload_2        
        //  2825: ldc_w           "GL_ARB_vertex_program"
        //  2828: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2833: ifeq            2852
        //  2836: aload_0        
        //  2837: ldc_w           "glNamedProgramLocalParameter4fEXT"
        //  2840: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2843: dup2_x1        
        //  2844: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParameter4fEXT:J
        //  2847: lconst_0       
        //  2848: lcmp           
        //  2849: ifeq            2856
        //  2852: iconst_1       
        //  2853: goto            2857
        //  2856: iconst_0       
        //  2857: iand           
        //  2858: aload_2        
        //  2859: ldc_w           "GL_ARB_vertex_program"
        //  2862: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2867: ifeq            2886
        //  2870: aload_0        
        //  2871: ldc_w           "glNamedProgramLocalParameter4fvEXT"
        //  2874: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2877: dup2_x1        
        //  2878: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParameter4fvEXT:J
        //  2881: lconst_0       
        //  2882: lcmp           
        //  2883: ifeq            2890
        //  2886: iconst_1       
        //  2887: goto            2891
        //  2890: iconst_0       
        //  2891: iand           
        //  2892: aload_2        
        //  2893: ldc_w           "GL_ARB_vertex_program"
        //  2896: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2901: ifeq            2920
        //  2904: aload_0        
        //  2905: ldc_w           "glGetNamedProgramLocalParameterdvEXT"
        //  2908: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2911: dup2_x1        
        //  2912: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedProgramLocalParameterdvEXT:J
        //  2915: lconst_0       
        //  2916: lcmp           
        //  2917: ifeq            2924
        //  2920: iconst_1       
        //  2921: goto            2925
        //  2924: iconst_0       
        //  2925: iand           
        //  2926: aload_2        
        //  2927: ldc_w           "GL_ARB_vertex_program"
        //  2930: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2935: ifeq            2954
        //  2938: aload_0        
        //  2939: ldc_w           "glGetNamedProgramLocalParameterfvEXT"
        //  2942: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2945: dup2_x1        
        //  2946: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedProgramLocalParameterfvEXT:J
        //  2949: lconst_0       
        //  2950: lcmp           
        //  2951: ifeq            2958
        //  2954: iconst_1       
        //  2955: goto            2959
        //  2958: iconst_0       
        //  2959: iand           
        //  2960: aload_2        
        //  2961: ldc_w           "GL_ARB_vertex_program"
        //  2964: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  2969: ifeq            2988
        //  2972: aload_0        
        //  2973: ldc_w           "glGetNamedProgramivEXT"
        //  2976: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  2979: dup2_x1        
        //  2980: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedProgramivEXT:J
        //  2983: lconst_0       
        //  2984: lcmp           
        //  2985: ifeq            2992
        //  2988: iconst_1       
        //  2989: goto            2993
        //  2992: iconst_0       
        //  2993: iand           
        //  2994: aload_2        
        //  2995: ldc_w           "GL_ARB_vertex_program"
        //  2998: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3003: ifeq            3022
        //  3006: aload_0        
        //  3007: ldc_w           "glGetNamedProgramStringEXT"
        //  3010: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3013: dup2_x1        
        //  3014: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedProgramStringEXT:J
        //  3017: lconst_0       
        //  3018: lcmp           
        //  3019: ifeq            3026
        //  3022: iconst_1       
        //  3023: goto            3027
        //  3026: iconst_0       
        //  3027: iand           
        //  3028: aload_2        
        //  3029: ldc_w           "OpenGL13"
        //  3032: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3037: ifeq            3056
        //  3040: aload_0        
        //  3041: ldc_w           "glCompressedTextureImage3DEXT"
        //  3044: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3047: dup2_x1        
        //  3048: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedTextureImage3DEXT:J
        //  3051: lconst_0       
        //  3052: lcmp           
        //  3053: ifeq            3060
        //  3056: iconst_1       
        //  3057: goto            3061
        //  3060: iconst_0       
        //  3061: iand           
        //  3062: aload_2        
        //  3063: ldc_w           "OpenGL13"
        //  3066: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3071: ifeq            3090
        //  3074: aload_0        
        //  3075: ldc_w           "glCompressedTextureImage2DEXT"
        //  3078: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3081: dup2_x1        
        //  3082: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedTextureImage2DEXT:J
        //  3085: lconst_0       
        //  3086: lcmp           
        //  3087: ifeq            3094
        //  3090: iconst_1       
        //  3091: goto            3095
        //  3094: iconst_0       
        //  3095: iand           
        //  3096: aload_2        
        //  3097: ldc_w           "OpenGL13"
        //  3100: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3105: ifeq            3124
        //  3108: aload_0        
        //  3109: ldc_w           "glCompressedTextureImage1DEXT"
        //  3112: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3115: dup2_x1        
        //  3116: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedTextureImage1DEXT:J
        //  3119: lconst_0       
        //  3120: lcmp           
        //  3121: ifeq            3128
        //  3124: iconst_1       
        //  3125: goto            3129
        //  3128: iconst_0       
        //  3129: iand           
        //  3130: aload_2        
        //  3131: ldc_w           "OpenGL13"
        //  3134: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3139: ifeq            3158
        //  3142: aload_0        
        //  3143: ldc_w           "glCompressedTextureSubImage3DEXT"
        //  3146: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3149: dup2_x1        
        //  3150: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedTextureSubImage3DEXT:J
        //  3153: lconst_0       
        //  3154: lcmp           
        //  3155: ifeq            3162
        //  3158: iconst_1       
        //  3159: goto            3163
        //  3162: iconst_0       
        //  3163: iand           
        //  3164: aload_2        
        //  3165: ldc_w           "OpenGL13"
        //  3168: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3173: ifeq            3192
        //  3176: aload_0        
        //  3177: ldc_w           "glCompressedTextureSubImage2DEXT"
        //  3180: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3183: dup2_x1        
        //  3184: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedTextureSubImage2DEXT:J
        //  3187: lconst_0       
        //  3188: lcmp           
        //  3189: ifeq            3196
        //  3192: iconst_1       
        //  3193: goto            3197
        //  3196: iconst_0       
        //  3197: iand           
        //  3198: aload_2        
        //  3199: ldc_w           "OpenGL13"
        //  3202: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3207: ifeq            3226
        //  3210: aload_0        
        //  3211: ldc_w           "glCompressedTextureSubImage1DEXT"
        //  3214: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3217: dup2_x1        
        //  3218: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedTextureSubImage1DEXT:J
        //  3221: lconst_0       
        //  3222: lcmp           
        //  3223: ifeq            3230
        //  3226: iconst_1       
        //  3227: goto            3231
        //  3230: iconst_0       
        //  3231: iand           
        //  3232: aload_2        
        //  3233: ldc_w           "OpenGL13"
        //  3236: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3241: ifeq            3260
        //  3244: aload_0        
        //  3245: ldc_w           "glGetCompressedTextureImageEXT"
        //  3248: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3251: dup2_x1        
        //  3252: putfield        org/lwjgl/opengl/ContextCapabilities.glGetCompressedTextureImageEXT:J
        //  3255: lconst_0       
        //  3256: lcmp           
        //  3257: ifeq            3264
        //  3260: iconst_1       
        //  3261: goto            3265
        //  3264: iconst_0       
        //  3265: iand           
        //  3266: aload_2        
        //  3267: ldc_w           "OpenGL13"
        //  3270: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3275: ifeq            3294
        //  3278: aload_0        
        //  3279: ldc_w           "glCompressedMultiTexImage3DEXT"
        //  3282: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3285: dup2_x1        
        //  3286: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedMultiTexImage3DEXT:J
        //  3289: lconst_0       
        //  3290: lcmp           
        //  3291: ifeq            3298
        //  3294: iconst_1       
        //  3295: goto            3299
        //  3298: iconst_0       
        //  3299: iand           
        //  3300: aload_2        
        //  3301: ldc_w           "OpenGL13"
        //  3304: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3309: ifeq            3328
        //  3312: aload_0        
        //  3313: ldc_w           "glCompressedMultiTexImage2DEXT"
        //  3316: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3319: dup2_x1        
        //  3320: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedMultiTexImage2DEXT:J
        //  3323: lconst_0       
        //  3324: lcmp           
        //  3325: ifeq            3332
        //  3328: iconst_1       
        //  3329: goto            3333
        //  3332: iconst_0       
        //  3333: iand           
        //  3334: aload_2        
        //  3335: ldc_w           "OpenGL13"
        //  3338: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3343: ifeq            3362
        //  3346: aload_0        
        //  3347: ldc_w           "glCompressedMultiTexImage1DEXT"
        //  3350: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3353: dup2_x1        
        //  3354: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedMultiTexImage1DEXT:J
        //  3357: lconst_0       
        //  3358: lcmp           
        //  3359: ifeq            3366
        //  3362: iconst_1       
        //  3363: goto            3367
        //  3366: iconst_0       
        //  3367: iand           
        //  3368: aload_2        
        //  3369: ldc_w           "OpenGL13"
        //  3372: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3377: ifeq            3396
        //  3380: aload_0        
        //  3381: ldc_w           "glCompressedMultiTexSubImage3DEXT"
        //  3384: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3387: dup2_x1        
        //  3388: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedMultiTexSubImage3DEXT:J
        //  3391: lconst_0       
        //  3392: lcmp           
        //  3393: ifeq            3400
        //  3396: iconst_1       
        //  3397: goto            3401
        //  3400: iconst_0       
        //  3401: iand           
        //  3402: aload_2        
        //  3403: ldc_w           "OpenGL13"
        //  3406: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3411: ifeq            3430
        //  3414: aload_0        
        //  3415: ldc_w           "glCompressedMultiTexSubImage2DEXT"
        //  3418: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3421: dup2_x1        
        //  3422: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedMultiTexSubImage2DEXT:J
        //  3425: lconst_0       
        //  3426: lcmp           
        //  3427: ifeq            3434
        //  3430: iconst_1       
        //  3431: goto            3435
        //  3434: iconst_0       
        //  3435: iand           
        //  3436: aload_2        
        //  3437: ldc_w           "OpenGL13"
        //  3440: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3445: ifeq            3464
        //  3448: aload_0        
        //  3449: ldc_w           "glCompressedMultiTexSubImage1DEXT"
        //  3452: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3455: dup2_x1        
        //  3456: putfield        org/lwjgl/opengl/ContextCapabilities.glCompressedMultiTexSubImage1DEXT:J
        //  3459: lconst_0       
        //  3460: lcmp           
        //  3461: ifeq            3468
        //  3464: iconst_1       
        //  3465: goto            3469
        //  3468: iconst_0       
        //  3469: iand           
        //  3470: aload_2        
        //  3471: ldc_w           "OpenGL13"
        //  3474: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3479: ifeq            3498
        //  3482: aload_0        
        //  3483: ldc_w           "glGetCompressedMultiTexImageEXT"
        //  3486: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3489: dup2_x1        
        //  3490: putfield        org/lwjgl/opengl/ContextCapabilities.glGetCompressedMultiTexImageEXT:J
        //  3493: lconst_0       
        //  3494: lcmp           
        //  3495: ifeq            3502
        //  3498: iconst_1       
        //  3499: goto            3503
        //  3502: iconst_0       
        //  3503: iand           
        //  3504: iload_1        
        //  3505: ifne            3536
        //  3508: aload_2        
        //  3509: ldc_w           "OpenGL13"
        //  3512: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3517: ifeq            3536
        //  3520: aload_0        
        //  3521: ldc_w           "glMatrixLoadTransposefEXT"
        //  3524: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3527: dup2_x1        
        //  3528: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixLoadTransposefEXT:J
        //  3531: lconst_0       
        //  3532: lcmp           
        //  3533: ifeq            3540
        //  3536: iconst_1       
        //  3537: goto            3541
        //  3540: iconst_0       
        //  3541: iand           
        //  3542: iload_1        
        //  3543: ifne            3574
        //  3546: aload_2        
        //  3547: ldc_w           "OpenGL13"
        //  3550: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3555: ifeq            3574
        //  3558: aload_0        
        //  3559: ldc_w           "glMatrixLoadTransposedEXT"
        //  3562: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3565: dup2_x1        
        //  3566: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixLoadTransposedEXT:J
        //  3569: lconst_0       
        //  3570: lcmp           
        //  3571: ifeq            3578
        //  3574: iconst_1       
        //  3575: goto            3579
        //  3578: iconst_0       
        //  3579: iand           
        //  3580: iload_1        
        //  3581: ifne            3612
        //  3584: aload_2        
        //  3585: ldc_w           "OpenGL13"
        //  3588: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3593: ifeq            3612
        //  3596: aload_0        
        //  3597: ldc_w           "glMatrixMultTransposefEXT"
        //  3600: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3603: dup2_x1        
        //  3604: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixMultTransposefEXT:J
        //  3607: lconst_0       
        //  3608: lcmp           
        //  3609: ifeq            3616
        //  3612: iconst_1       
        //  3613: goto            3617
        //  3616: iconst_0       
        //  3617: iand           
        //  3618: iload_1        
        //  3619: ifne            3650
        //  3622: aload_2        
        //  3623: ldc_w           "OpenGL13"
        //  3626: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3631: ifeq            3650
        //  3634: aload_0        
        //  3635: ldc_w           "glMatrixMultTransposedEXT"
        //  3638: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3641: dup2_x1        
        //  3642: putfield        org/lwjgl/opengl/ContextCapabilities.glMatrixMultTransposedEXT:J
        //  3645: lconst_0       
        //  3646: lcmp           
        //  3647: ifeq            3654
        //  3650: iconst_1       
        //  3651: goto            3655
        //  3654: iconst_0       
        //  3655: iand           
        //  3656: aload_2        
        //  3657: ldc_w           "OpenGL15"
        //  3660: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3665: ifeq            3684
        //  3668: aload_0        
        //  3669: ldc_w           "glNamedBufferDataEXT"
        //  3672: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3675: dup2_x1        
        //  3676: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedBufferDataEXT:J
        //  3679: lconst_0       
        //  3680: lcmp           
        //  3681: ifeq            3688
        //  3684: iconst_1       
        //  3685: goto            3689
        //  3688: iconst_0       
        //  3689: iand           
        //  3690: aload_2        
        //  3691: ldc_w           "OpenGL15"
        //  3694: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3699: ifeq            3718
        //  3702: aload_0        
        //  3703: ldc_w           "glNamedBufferSubDataEXT"
        //  3706: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3709: dup2_x1        
        //  3710: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedBufferSubDataEXT:J
        //  3713: lconst_0       
        //  3714: lcmp           
        //  3715: ifeq            3722
        //  3718: iconst_1       
        //  3719: goto            3723
        //  3722: iconst_0       
        //  3723: iand           
        //  3724: aload_2        
        //  3725: ldc_w           "OpenGL15"
        //  3728: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3733: ifeq            3752
        //  3736: aload_0        
        //  3737: ldc_w           "glMapNamedBufferEXT"
        //  3740: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3743: dup2_x1        
        //  3744: putfield        org/lwjgl/opengl/ContextCapabilities.glMapNamedBufferEXT:J
        //  3747: lconst_0       
        //  3748: lcmp           
        //  3749: ifeq            3756
        //  3752: iconst_1       
        //  3753: goto            3757
        //  3756: iconst_0       
        //  3757: iand           
        //  3758: aload_2        
        //  3759: ldc_w           "OpenGL15"
        //  3762: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3767: ifeq            3786
        //  3770: aload_0        
        //  3771: ldc_w           "glUnmapNamedBufferEXT"
        //  3774: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3777: dup2_x1        
        //  3778: putfield        org/lwjgl/opengl/ContextCapabilities.glUnmapNamedBufferEXT:J
        //  3781: lconst_0       
        //  3782: lcmp           
        //  3783: ifeq            3790
        //  3786: iconst_1       
        //  3787: goto            3791
        //  3790: iconst_0       
        //  3791: iand           
        //  3792: aload_2        
        //  3793: ldc_w           "OpenGL15"
        //  3796: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3801: ifeq            3820
        //  3804: aload_0        
        //  3805: ldc_w           "glGetNamedBufferParameterivEXT"
        //  3808: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3811: dup2_x1        
        //  3812: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedBufferParameterivEXT:J
        //  3815: lconst_0       
        //  3816: lcmp           
        //  3817: ifeq            3824
        //  3820: iconst_1       
        //  3821: goto            3825
        //  3824: iconst_0       
        //  3825: iand           
        //  3826: aload_2        
        //  3827: ldc_w           "OpenGL15"
        //  3830: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3835: ifeq            3854
        //  3838: aload_0        
        //  3839: ldc_w           "glGetNamedBufferPointervEXT"
        //  3842: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3845: dup2_x1        
        //  3846: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedBufferPointervEXT:J
        //  3849: lconst_0       
        //  3850: lcmp           
        //  3851: ifeq            3858
        //  3854: iconst_1       
        //  3855: goto            3859
        //  3858: iconst_0       
        //  3859: iand           
        //  3860: aload_2        
        //  3861: ldc_w           "OpenGL15"
        //  3864: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3869: ifeq            3888
        //  3872: aload_0        
        //  3873: ldc_w           "glGetNamedBufferSubDataEXT"
        //  3876: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3879: dup2_x1        
        //  3880: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedBufferSubDataEXT:J
        //  3883: lconst_0       
        //  3884: lcmp           
        //  3885: ifeq            3892
        //  3888: iconst_1       
        //  3889: goto            3893
        //  3892: iconst_0       
        //  3893: iand           
        //  3894: aload_2        
        //  3895: ldc_w           "OpenGL20"
        //  3898: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3903: ifeq            3922
        //  3906: aload_0        
        //  3907: ldc_w           "glProgramUniform1fEXT"
        //  3910: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3913: dup2_x1        
        //  3914: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform1fEXT:J
        //  3917: lconst_0       
        //  3918: lcmp           
        //  3919: ifeq            3926
        //  3922: iconst_1       
        //  3923: goto            3927
        //  3926: iconst_0       
        //  3927: iand           
        //  3928: aload_2        
        //  3929: ldc_w           "OpenGL20"
        //  3932: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3937: ifeq            3956
        //  3940: aload_0        
        //  3941: ldc_w           "glProgramUniform2fEXT"
        //  3944: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3947: dup2_x1        
        //  3948: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform2fEXT:J
        //  3951: lconst_0       
        //  3952: lcmp           
        //  3953: ifeq            3960
        //  3956: iconst_1       
        //  3957: goto            3961
        //  3960: iconst_0       
        //  3961: iand           
        //  3962: aload_2        
        //  3963: ldc_w           "OpenGL20"
        //  3966: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  3971: ifeq            3990
        //  3974: aload_0        
        //  3975: ldc_w           "glProgramUniform3fEXT"
        //  3978: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  3981: dup2_x1        
        //  3982: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform3fEXT:J
        //  3985: lconst_0       
        //  3986: lcmp           
        //  3987: ifeq            3994
        //  3990: iconst_1       
        //  3991: goto            3995
        //  3994: iconst_0       
        //  3995: iand           
        //  3996: aload_2        
        //  3997: ldc_w           "OpenGL20"
        //  4000: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4005: ifeq            4024
        //  4008: aload_0        
        //  4009: ldc_w           "glProgramUniform4fEXT"
        //  4012: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4015: dup2_x1        
        //  4016: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform4fEXT:J
        //  4019: lconst_0       
        //  4020: lcmp           
        //  4021: ifeq            4028
        //  4024: iconst_1       
        //  4025: goto            4029
        //  4028: iconst_0       
        //  4029: iand           
        //  4030: aload_2        
        //  4031: ldc_w           "OpenGL20"
        //  4034: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4039: ifeq            4058
        //  4042: aload_0        
        //  4043: ldc_w           "glProgramUniform1iEXT"
        //  4046: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4049: dup2_x1        
        //  4050: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform1iEXT:J
        //  4053: lconst_0       
        //  4054: lcmp           
        //  4055: ifeq            4062
        //  4058: iconst_1       
        //  4059: goto            4063
        //  4062: iconst_0       
        //  4063: iand           
        //  4064: aload_2        
        //  4065: ldc_w           "OpenGL20"
        //  4068: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4073: ifeq            4092
        //  4076: aload_0        
        //  4077: ldc_w           "glProgramUniform2iEXT"
        //  4080: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4083: dup2_x1        
        //  4084: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform2iEXT:J
        //  4087: lconst_0       
        //  4088: lcmp           
        //  4089: ifeq            4096
        //  4092: iconst_1       
        //  4093: goto            4097
        //  4096: iconst_0       
        //  4097: iand           
        //  4098: aload_2        
        //  4099: ldc_w           "OpenGL20"
        //  4102: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4107: ifeq            4126
        //  4110: aload_0        
        //  4111: ldc_w           "glProgramUniform3iEXT"
        //  4114: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4117: dup2_x1        
        //  4118: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform3iEXT:J
        //  4121: lconst_0       
        //  4122: lcmp           
        //  4123: ifeq            4130
        //  4126: iconst_1       
        //  4127: goto            4131
        //  4130: iconst_0       
        //  4131: iand           
        //  4132: aload_2        
        //  4133: ldc_w           "OpenGL20"
        //  4136: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4141: ifeq            4160
        //  4144: aload_0        
        //  4145: ldc_w           "glProgramUniform4iEXT"
        //  4148: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4151: dup2_x1        
        //  4152: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform4iEXT:J
        //  4155: lconst_0       
        //  4156: lcmp           
        //  4157: ifeq            4164
        //  4160: iconst_1       
        //  4161: goto            4165
        //  4164: iconst_0       
        //  4165: iand           
        //  4166: aload_2        
        //  4167: ldc_w           "OpenGL20"
        //  4170: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4175: ifeq            4194
        //  4178: aload_0        
        //  4179: ldc_w           "glProgramUniform1fvEXT"
        //  4182: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4185: dup2_x1        
        //  4186: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform1fvEXT:J
        //  4189: lconst_0       
        //  4190: lcmp           
        //  4191: ifeq            4198
        //  4194: iconst_1       
        //  4195: goto            4199
        //  4198: iconst_0       
        //  4199: iand           
        //  4200: aload_2        
        //  4201: ldc_w           "OpenGL20"
        //  4204: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4209: ifeq            4228
        //  4212: aload_0        
        //  4213: ldc_w           "glProgramUniform2fvEXT"
        //  4216: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4219: dup2_x1        
        //  4220: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform2fvEXT:J
        //  4223: lconst_0       
        //  4224: lcmp           
        //  4225: ifeq            4232
        //  4228: iconst_1       
        //  4229: goto            4233
        //  4232: iconst_0       
        //  4233: iand           
        //  4234: aload_2        
        //  4235: ldc_w           "OpenGL20"
        //  4238: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4243: ifeq            4262
        //  4246: aload_0        
        //  4247: ldc_w           "glProgramUniform3fvEXT"
        //  4250: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4253: dup2_x1        
        //  4254: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform3fvEXT:J
        //  4257: lconst_0       
        //  4258: lcmp           
        //  4259: ifeq            4266
        //  4262: iconst_1       
        //  4263: goto            4267
        //  4266: iconst_0       
        //  4267: iand           
        //  4268: aload_2        
        //  4269: ldc_w           "OpenGL20"
        //  4272: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4277: ifeq            4296
        //  4280: aload_0        
        //  4281: ldc_w           "glProgramUniform4fvEXT"
        //  4284: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4287: dup2_x1        
        //  4288: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform4fvEXT:J
        //  4291: lconst_0       
        //  4292: lcmp           
        //  4293: ifeq            4300
        //  4296: iconst_1       
        //  4297: goto            4301
        //  4300: iconst_0       
        //  4301: iand           
        //  4302: aload_2        
        //  4303: ldc_w           "OpenGL20"
        //  4306: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4311: ifeq            4330
        //  4314: aload_0        
        //  4315: ldc_w           "glProgramUniform1ivEXT"
        //  4318: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4321: dup2_x1        
        //  4322: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform1ivEXT:J
        //  4325: lconst_0       
        //  4326: lcmp           
        //  4327: ifeq            4334
        //  4330: iconst_1       
        //  4331: goto            4335
        //  4334: iconst_0       
        //  4335: iand           
        //  4336: aload_2        
        //  4337: ldc_w           "OpenGL20"
        //  4340: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4345: ifeq            4364
        //  4348: aload_0        
        //  4349: ldc_w           "glProgramUniform2ivEXT"
        //  4352: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4355: dup2_x1        
        //  4356: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform2ivEXT:J
        //  4359: lconst_0       
        //  4360: lcmp           
        //  4361: ifeq            4368
        //  4364: iconst_1       
        //  4365: goto            4369
        //  4368: iconst_0       
        //  4369: iand           
        //  4370: aload_2        
        //  4371: ldc_w           "OpenGL20"
        //  4374: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4379: ifeq            4398
        //  4382: aload_0        
        //  4383: ldc_w           "glProgramUniform3ivEXT"
        //  4386: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4389: dup2_x1        
        //  4390: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform3ivEXT:J
        //  4393: lconst_0       
        //  4394: lcmp           
        //  4395: ifeq            4402
        //  4398: iconst_1       
        //  4399: goto            4403
        //  4402: iconst_0       
        //  4403: iand           
        //  4404: aload_2        
        //  4405: ldc_w           "OpenGL20"
        //  4408: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4413: ifeq            4432
        //  4416: aload_0        
        //  4417: ldc_w           "glProgramUniform4ivEXT"
        //  4420: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4423: dup2_x1        
        //  4424: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform4ivEXT:J
        //  4427: lconst_0       
        //  4428: lcmp           
        //  4429: ifeq            4436
        //  4432: iconst_1       
        //  4433: goto            4437
        //  4436: iconst_0       
        //  4437: iand           
        //  4438: aload_2        
        //  4439: ldc_w           "OpenGL20"
        //  4442: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4447: ifeq            4466
        //  4450: aload_0        
        //  4451: ldc_w           "glProgramUniformMatrix2fvEXT"
        //  4454: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4457: dup2_x1        
        //  4458: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniformMatrix2fvEXT:J
        //  4461: lconst_0       
        //  4462: lcmp           
        //  4463: ifeq            4470
        //  4466: iconst_1       
        //  4467: goto            4471
        //  4470: iconst_0       
        //  4471: iand           
        //  4472: aload_2        
        //  4473: ldc_w           "OpenGL20"
        //  4476: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4481: ifeq            4500
        //  4484: aload_0        
        //  4485: ldc_w           "glProgramUniformMatrix3fvEXT"
        //  4488: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4491: dup2_x1        
        //  4492: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniformMatrix3fvEXT:J
        //  4495: lconst_0       
        //  4496: lcmp           
        //  4497: ifeq            4504
        //  4500: iconst_1       
        //  4501: goto            4505
        //  4504: iconst_0       
        //  4505: iand           
        //  4506: aload_2        
        //  4507: ldc_w           "OpenGL20"
        //  4510: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4515: ifeq            4534
        //  4518: aload_0        
        //  4519: ldc_w           "glProgramUniformMatrix4fvEXT"
        //  4522: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4525: dup2_x1        
        //  4526: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniformMatrix4fvEXT:J
        //  4529: lconst_0       
        //  4530: lcmp           
        //  4531: ifeq            4538
        //  4534: iconst_1       
        //  4535: goto            4539
        //  4538: iconst_0       
        //  4539: iand           
        //  4540: aload_2        
        //  4541: ldc_w           "OpenGL21"
        //  4544: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4549: ifeq            4568
        //  4552: aload_0        
        //  4553: ldc_w           "glProgramUniformMatrix2x3fvEXT"
        //  4556: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4559: dup2_x1        
        //  4560: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniformMatrix2x3fvEXT:J
        //  4563: lconst_0       
        //  4564: lcmp           
        //  4565: ifeq            4572
        //  4568: iconst_1       
        //  4569: goto            4573
        //  4572: iconst_0       
        //  4573: iand           
        //  4574: aload_2        
        //  4575: ldc_w           "OpenGL21"
        //  4578: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4583: ifeq            4602
        //  4586: aload_0        
        //  4587: ldc_w           "glProgramUniformMatrix3x2fvEXT"
        //  4590: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4593: dup2_x1        
        //  4594: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniformMatrix3x2fvEXT:J
        //  4597: lconst_0       
        //  4598: lcmp           
        //  4599: ifeq            4606
        //  4602: iconst_1       
        //  4603: goto            4607
        //  4606: iconst_0       
        //  4607: iand           
        //  4608: aload_2        
        //  4609: ldc_w           "OpenGL21"
        //  4612: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4617: ifeq            4636
        //  4620: aload_0        
        //  4621: ldc_w           "glProgramUniformMatrix2x4fvEXT"
        //  4624: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4627: dup2_x1        
        //  4628: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniformMatrix2x4fvEXT:J
        //  4631: lconst_0       
        //  4632: lcmp           
        //  4633: ifeq            4640
        //  4636: iconst_1       
        //  4637: goto            4641
        //  4640: iconst_0       
        //  4641: iand           
        //  4642: aload_2        
        //  4643: ldc_w           "OpenGL21"
        //  4646: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4651: ifeq            4670
        //  4654: aload_0        
        //  4655: ldc_w           "glProgramUniformMatrix4x2fvEXT"
        //  4658: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4661: dup2_x1        
        //  4662: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniformMatrix4x2fvEXT:J
        //  4665: lconst_0       
        //  4666: lcmp           
        //  4667: ifeq            4674
        //  4670: iconst_1       
        //  4671: goto            4675
        //  4674: iconst_0       
        //  4675: iand           
        //  4676: aload_2        
        //  4677: ldc_w           "OpenGL21"
        //  4680: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4685: ifeq            4704
        //  4688: aload_0        
        //  4689: ldc_w           "glProgramUniformMatrix3x4fvEXT"
        //  4692: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4695: dup2_x1        
        //  4696: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniformMatrix3x4fvEXT:J
        //  4699: lconst_0       
        //  4700: lcmp           
        //  4701: ifeq            4708
        //  4704: iconst_1       
        //  4705: goto            4709
        //  4708: iconst_0       
        //  4709: iand           
        //  4710: aload_2        
        //  4711: ldc_w           "OpenGL21"
        //  4714: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4719: ifeq            4738
        //  4722: aload_0        
        //  4723: ldc_w           "glProgramUniformMatrix4x3fvEXT"
        //  4726: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4729: dup2_x1        
        //  4730: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniformMatrix4x3fvEXT:J
        //  4733: lconst_0       
        //  4734: lcmp           
        //  4735: ifeq            4742
        //  4738: iconst_1       
        //  4739: goto            4743
        //  4742: iconst_0       
        //  4743: iand           
        //  4744: aload_2        
        //  4745: ldc_w           "GL_EXT_texture_buffer_object"
        //  4748: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4753: ifeq            4772
        //  4756: aload_0        
        //  4757: ldc_w           "glTextureBufferEXT"
        //  4760: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4763: dup2_x1        
        //  4764: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureBufferEXT:J
        //  4767: lconst_0       
        //  4768: lcmp           
        //  4769: ifeq            4776
        //  4772: iconst_1       
        //  4773: goto            4777
        //  4776: iconst_0       
        //  4777: iand           
        //  4778: aload_2        
        //  4779: ldc_w           "GL_EXT_texture_buffer_object"
        //  4782: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4787: ifeq            4806
        //  4790: aload_0        
        //  4791: ldc_w           "glMultiTexBufferEXT"
        //  4794: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4797: dup2_x1        
        //  4798: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexBufferEXT:J
        //  4801: lconst_0       
        //  4802: lcmp           
        //  4803: ifeq            4810
        //  4806: iconst_1       
        //  4807: goto            4811
        //  4810: iconst_0       
        //  4811: iand           
        //  4812: aload_2        
        //  4813: ldc_w           "GL_EXT_texture_integer"
        //  4816: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4821: ifeq            4840
        //  4824: aload_0        
        //  4825: ldc_w           "glTextureParameterIivEXT"
        //  4828: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4831: dup2_x1        
        //  4832: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureParameterIivEXT:J
        //  4835: lconst_0       
        //  4836: lcmp           
        //  4837: ifeq            4844
        //  4840: iconst_1       
        //  4841: goto            4845
        //  4844: iconst_0       
        //  4845: iand           
        //  4846: aload_2        
        //  4847: ldc_w           "GL_EXT_texture_integer"
        //  4850: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4855: ifeq            4874
        //  4858: aload_0        
        //  4859: ldc_w           "glTextureParameterIuivEXT"
        //  4862: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4865: dup2_x1        
        //  4866: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureParameterIuivEXT:J
        //  4869: lconst_0       
        //  4870: lcmp           
        //  4871: ifeq            4878
        //  4874: iconst_1       
        //  4875: goto            4879
        //  4878: iconst_0       
        //  4879: iand           
        //  4880: aload_2        
        //  4881: ldc_w           "GL_EXT_texture_integer"
        //  4884: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4889: ifeq            4908
        //  4892: aload_0        
        //  4893: ldc_w           "glGetTextureParameterIivEXT"
        //  4896: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4899: dup2_x1        
        //  4900: putfield        org/lwjgl/opengl/ContextCapabilities.glGetTextureParameterIivEXT:J
        //  4903: lconst_0       
        //  4904: lcmp           
        //  4905: ifeq            4912
        //  4908: iconst_1       
        //  4909: goto            4913
        //  4912: iconst_0       
        //  4913: iand           
        //  4914: aload_2        
        //  4915: ldc_w           "GL_EXT_texture_integer"
        //  4918: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4923: ifeq            4942
        //  4926: aload_0        
        //  4927: ldc_w           "glGetTextureParameterIuivEXT"
        //  4930: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4933: dup2_x1        
        //  4934: putfield        org/lwjgl/opengl/ContextCapabilities.glGetTextureParameterIuivEXT:J
        //  4937: lconst_0       
        //  4938: lcmp           
        //  4939: ifeq            4946
        //  4942: iconst_1       
        //  4943: goto            4947
        //  4946: iconst_0       
        //  4947: iand           
        //  4948: aload_2        
        //  4949: ldc_w           "GL_EXT_texture_integer"
        //  4952: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4957: ifeq            4976
        //  4960: aload_0        
        //  4961: ldc_w           "glMultiTexParameterIivEXT"
        //  4964: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  4967: dup2_x1        
        //  4968: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexParameterIivEXT:J
        //  4971: lconst_0       
        //  4972: lcmp           
        //  4973: ifeq            4980
        //  4976: iconst_1       
        //  4977: goto            4981
        //  4980: iconst_0       
        //  4981: iand           
        //  4982: aload_2        
        //  4983: ldc_w           "GL_EXT_texture_integer"
        //  4986: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  4991: ifeq            5010
        //  4994: aload_0        
        //  4995: ldc_w           "glMultiTexParameterIuivEXT"
        //  4998: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5001: dup2_x1        
        //  5002: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexParameterIuivEXT:J
        //  5005: lconst_0       
        //  5006: lcmp           
        //  5007: ifeq            5014
        //  5010: iconst_1       
        //  5011: goto            5015
        //  5014: iconst_0       
        //  5015: iand           
        //  5016: aload_2        
        //  5017: ldc_w           "GL_EXT_texture_integer"
        //  5020: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5025: ifeq            5044
        //  5028: aload_0        
        //  5029: ldc_w           "glGetMultiTexParameterIivEXT"
        //  5032: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5035: dup2_x1        
        //  5036: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexParameterIivEXT:J
        //  5039: lconst_0       
        //  5040: lcmp           
        //  5041: ifeq            5048
        //  5044: iconst_1       
        //  5045: goto            5049
        //  5048: iconst_0       
        //  5049: iand           
        //  5050: aload_2        
        //  5051: ldc_w           "GL_EXT_texture_integer"
        //  5054: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5059: ifeq            5078
        //  5062: aload_0        
        //  5063: ldc_w           "glGetMultiTexParameterIuivEXT"
        //  5066: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5069: dup2_x1        
        //  5070: putfield        org/lwjgl/opengl/ContextCapabilities.glGetMultiTexParameterIuivEXT:J
        //  5073: lconst_0       
        //  5074: lcmp           
        //  5075: ifeq            5082
        //  5078: iconst_1       
        //  5079: goto            5083
        //  5082: iconst_0       
        //  5083: iand           
        //  5084: aload_2        
        //  5085: ldc_w           "GL_EXT_gpu_shader4"
        //  5088: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5093: ifeq            5112
        //  5096: aload_0        
        //  5097: ldc_w           "glProgramUniform1uiEXT"
        //  5100: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5103: dup2_x1        
        //  5104: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform1uiEXT:J
        //  5107: lconst_0       
        //  5108: lcmp           
        //  5109: ifeq            5116
        //  5112: iconst_1       
        //  5113: goto            5117
        //  5116: iconst_0       
        //  5117: iand           
        //  5118: aload_2        
        //  5119: ldc_w           "GL_EXT_gpu_shader4"
        //  5122: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5127: ifeq            5146
        //  5130: aload_0        
        //  5131: ldc_w           "glProgramUniform2uiEXT"
        //  5134: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5137: dup2_x1        
        //  5138: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform2uiEXT:J
        //  5141: lconst_0       
        //  5142: lcmp           
        //  5143: ifeq            5150
        //  5146: iconst_1       
        //  5147: goto            5151
        //  5150: iconst_0       
        //  5151: iand           
        //  5152: aload_2        
        //  5153: ldc_w           "GL_EXT_gpu_shader4"
        //  5156: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5161: ifeq            5180
        //  5164: aload_0        
        //  5165: ldc_w           "glProgramUniform3uiEXT"
        //  5168: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5171: dup2_x1        
        //  5172: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform3uiEXT:J
        //  5175: lconst_0       
        //  5176: lcmp           
        //  5177: ifeq            5184
        //  5180: iconst_1       
        //  5181: goto            5185
        //  5184: iconst_0       
        //  5185: iand           
        //  5186: aload_2        
        //  5187: ldc_w           "GL_EXT_gpu_shader4"
        //  5190: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5195: ifeq            5214
        //  5198: aload_0        
        //  5199: ldc_w           "glProgramUniform4uiEXT"
        //  5202: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5205: dup2_x1        
        //  5206: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform4uiEXT:J
        //  5209: lconst_0       
        //  5210: lcmp           
        //  5211: ifeq            5218
        //  5214: iconst_1       
        //  5215: goto            5219
        //  5218: iconst_0       
        //  5219: iand           
        //  5220: aload_2        
        //  5221: ldc_w           "GL_EXT_gpu_shader4"
        //  5224: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5229: ifeq            5248
        //  5232: aload_0        
        //  5233: ldc_w           "glProgramUniform1uivEXT"
        //  5236: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5239: dup2_x1        
        //  5240: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform1uivEXT:J
        //  5243: lconst_0       
        //  5244: lcmp           
        //  5245: ifeq            5252
        //  5248: iconst_1       
        //  5249: goto            5253
        //  5252: iconst_0       
        //  5253: iand           
        //  5254: aload_2        
        //  5255: ldc_w           "GL_EXT_gpu_shader4"
        //  5258: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5263: ifeq            5282
        //  5266: aload_0        
        //  5267: ldc_w           "glProgramUniform2uivEXT"
        //  5270: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5273: dup2_x1        
        //  5274: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform2uivEXT:J
        //  5277: lconst_0       
        //  5278: lcmp           
        //  5279: ifeq            5286
        //  5282: iconst_1       
        //  5283: goto            5287
        //  5286: iconst_0       
        //  5287: iand           
        //  5288: aload_2        
        //  5289: ldc_w           "GL_EXT_gpu_shader4"
        //  5292: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5297: ifeq            5316
        //  5300: aload_0        
        //  5301: ldc_w           "glProgramUniform3uivEXT"
        //  5304: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5307: dup2_x1        
        //  5308: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform3uivEXT:J
        //  5311: lconst_0       
        //  5312: lcmp           
        //  5313: ifeq            5320
        //  5316: iconst_1       
        //  5317: goto            5321
        //  5320: iconst_0       
        //  5321: iand           
        //  5322: aload_2        
        //  5323: ldc_w           "GL_EXT_gpu_shader4"
        //  5326: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5331: ifeq            5350
        //  5334: aload_0        
        //  5335: ldc_w           "glProgramUniform4uivEXT"
        //  5338: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5341: dup2_x1        
        //  5342: putfield        org/lwjgl/opengl/ContextCapabilities.glProgramUniform4uivEXT:J
        //  5345: lconst_0       
        //  5346: lcmp           
        //  5347: ifeq            5354
        //  5350: iconst_1       
        //  5351: goto            5355
        //  5354: iconst_0       
        //  5355: iand           
        //  5356: aload_2        
        //  5357: ldc_w           "GL_EXT_gpu_program_parameters"
        //  5360: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5365: ifeq            5384
        //  5368: aload_0        
        //  5369: ldc_w           "glNamedProgramLocalParameters4fvEXT"
        //  5372: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5375: dup2_x1        
        //  5376: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParameters4fvEXT:J
        //  5379: lconst_0       
        //  5380: lcmp           
        //  5381: ifeq            5388
        //  5384: iconst_1       
        //  5385: goto            5389
        //  5388: iconst_0       
        //  5389: iand           
        //  5390: aload_2        
        //  5391: ldc_w           "GL_NV_gpu_program4"
        //  5394: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5399: ifeq            5418
        //  5402: aload_0        
        //  5403: ldc_w           "glNamedProgramLocalParameterI4iEXT"
        //  5406: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5409: dup2_x1        
        //  5410: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParameterI4iEXT:J
        //  5413: lconst_0       
        //  5414: lcmp           
        //  5415: ifeq            5422
        //  5418: iconst_1       
        //  5419: goto            5423
        //  5422: iconst_0       
        //  5423: iand           
        //  5424: aload_2        
        //  5425: ldc_w           "GL_NV_gpu_program4"
        //  5428: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5433: ifeq            5452
        //  5436: aload_0        
        //  5437: ldc_w           "glNamedProgramLocalParameterI4ivEXT"
        //  5440: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5443: dup2_x1        
        //  5444: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParameterI4ivEXT:J
        //  5447: lconst_0       
        //  5448: lcmp           
        //  5449: ifeq            5456
        //  5452: iconst_1       
        //  5453: goto            5457
        //  5456: iconst_0       
        //  5457: iand           
        //  5458: aload_2        
        //  5459: ldc_w           "GL_NV_gpu_program4"
        //  5462: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5467: ifeq            5486
        //  5470: aload_0        
        //  5471: ldc_w           "glNamedProgramLocalParametersI4ivEXT"
        //  5474: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5477: dup2_x1        
        //  5478: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParametersI4ivEXT:J
        //  5481: lconst_0       
        //  5482: lcmp           
        //  5483: ifeq            5490
        //  5486: iconst_1       
        //  5487: goto            5491
        //  5490: iconst_0       
        //  5491: iand           
        //  5492: aload_2        
        //  5493: ldc_w           "GL_NV_gpu_program4"
        //  5496: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5501: ifeq            5520
        //  5504: aload_0        
        //  5505: ldc_w           "glNamedProgramLocalParameterI4uiEXT"
        //  5508: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5511: dup2_x1        
        //  5512: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParameterI4uiEXT:J
        //  5515: lconst_0       
        //  5516: lcmp           
        //  5517: ifeq            5524
        //  5520: iconst_1       
        //  5521: goto            5525
        //  5524: iconst_0       
        //  5525: iand           
        //  5526: aload_2        
        //  5527: ldc_w           "GL_NV_gpu_program4"
        //  5530: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5535: ifeq            5554
        //  5538: aload_0        
        //  5539: ldc_w           "glNamedProgramLocalParameterI4uivEXT"
        //  5542: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5545: dup2_x1        
        //  5546: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParameterI4uivEXT:J
        //  5549: lconst_0       
        //  5550: lcmp           
        //  5551: ifeq            5558
        //  5554: iconst_1       
        //  5555: goto            5559
        //  5558: iconst_0       
        //  5559: iand           
        //  5560: aload_2        
        //  5561: ldc_w           "GL_NV_gpu_program4"
        //  5564: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5569: ifeq            5588
        //  5572: aload_0        
        //  5573: ldc_w           "glNamedProgramLocalParametersI4uivEXT"
        //  5576: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5579: dup2_x1        
        //  5580: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedProgramLocalParametersI4uivEXT:J
        //  5583: lconst_0       
        //  5584: lcmp           
        //  5585: ifeq            5592
        //  5588: iconst_1       
        //  5589: goto            5593
        //  5592: iconst_0       
        //  5593: iand           
        //  5594: aload_2        
        //  5595: ldc_w           "GL_NV_gpu_program4"
        //  5598: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5603: ifeq            5622
        //  5606: aload_0        
        //  5607: ldc_w           "glGetNamedProgramLocalParameterIivEXT"
        //  5610: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5613: dup2_x1        
        //  5614: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedProgramLocalParameterIivEXT:J
        //  5617: lconst_0       
        //  5618: lcmp           
        //  5619: ifeq            5626
        //  5622: iconst_1       
        //  5623: goto            5627
        //  5626: iconst_0       
        //  5627: iand           
        //  5628: aload_2        
        //  5629: ldc_w           "GL_NV_gpu_program4"
        //  5632: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5637: ifeq            5656
        //  5640: aload_0        
        //  5641: ldc_w           "glGetNamedProgramLocalParameterIuivEXT"
        //  5644: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5647: dup2_x1        
        //  5648: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedProgramLocalParameterIuivEXT:J
        //  5651: lconst_0       
        //  5652: lcmp           
        //  5653: ifeq            5660
        //  5656: iconst_1       
        //  5657: goto            5661
        //  5660: iconst_0       
        //  5661: iand           
        //  5662: aload_2        
        //  5663: ldc_w           "OpenGL30"
        //  5666: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5671: ifne            5686
        //  5674: aload_2        
        //  5675: ldc_w           "GL_EXT_framebuffer_object"
        //  5678: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5683: ifeq            5702
        //  5686: aload_0        
        //  5687: ldc_w           "glNamedRenderbufferStorageEXT"
        //  5690: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5693: dup2_x1        
        //  5694: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedRenderbufferStorageEXT:J
        //  5697: lconst_0       
        //  5698: lcmp           
        //  5699: ifeq            5706
        //  5702: iconst_1       
        //  5703: goto            5707
        //  5706: iconst_0       
        //  5707: iand           
        //  5708: aload_2        
        //  5709: ldc_w           "OpenGL30"
        //  5712: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5717: ifne            5732
        //  5720: aload_2        
        //  5721: ldc_w           "GL_EXT_framebuffer_object"
        //  5724: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5729: ifeq            5748
        //  5732: aload_0        
        //  5733: ldc_w           "glGetNamedRenderbufferParameterivEXT"
        //  5736: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5739: dup2_x1        
        //  5740: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedRenderbufferParameterivEXT:J
        //  5743: lconst_0       
        //  5744: lcmp           
        //  5745: ifeq            5752
        //  5748: iconst_1       
        //  5749: goto            5753
        //  5752: iconst_0       
        //  5753: iand           
        //  5754: aload_2        
        //  5755: ldc_w           "OpenGL30"
        //  5758: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5763: ifne            5778
        //  5766: aload_2        
        //  5767: ldc_w           "GL_EXT_framebuffer_multisample"
        //  5770: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5775: ifeq            5794
        //  5778: aload_0        
        //  5779: ldc_w           "glNamedRenderbufferStorageMultisampleEXT"
        //  5782: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5785: dup2_x1        
        //  5786: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedRenderbufferStorageMultisampleEXT:J
        //  5789: lconst_0       
        //  5790: lcmp           
        //  5791: ifeq            5798
        //  5794: iconst_1       
        //  5795: goto            5799
        //  5798: iconst_0       
        //  5799: iand           
        //  5800: aload_2        
        //  5801: ldc_w           "GL_NV_framebuffer_multisample_coverage"
        //  5804: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5809: ifeq            5828
        //  5812: aload_0        
        //  5813: ldc_w           "glNamedRenderbufferStorageMultisampleCoverageEXT"
        //  5816: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5819: dup2_x1        
        //  5820: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedRenderbufferStorageMultisampleCoverageEXT:J
        //  5823: lconst_0       
        //  5824: lcmp           
        //  5825: ifeq            5832
        //  5828: iconst_1       
        //  5829: goto            5833
        //  5832: iconst_0       
        //  5833: iand           
        //  5834: aload_2        
        //  5835: ldc_w           "OpenGL30"
        //  5838: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5843: ifne            5858
        //  5846: aload_2        
        //  5847: ldc_w           "GL_EXT_framebuffer_object"
        //  5850: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5855: ifeq            5874
        //  5858: aload_0        
        //  5859: ldc_w           "glCheckNamedFramebufferStatusEXT"
        //  5862: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5865: dup2_x1        
        //  5866: putfield        org/lwjgl/opengl/ContextCapabilities.glCheckNamedFramebufferStatusEXT:J
        //  5869: lconst_0       
        //  5870: lcmp           
        //  5871: ifeq            5878
        //  5874: iconst_1       
        //  5875: goto            5879
        //  5878: iconst_0       
        //  5879: iand           
        //  5880: aload_2        
        //  5881: ldc_w           "OpenGL30"
        //  5884: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5889: ifne            5904
        //  5892: aload_2        
        //  5893: ldc_w           "GL_EXT_framebuffer_object"
        //  5896: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5901: ifeq            5920
        //  5904: aload_0        
        //  5905: ldc_w           "glNamedFramebufferTexture1DEXT"
        //  5908: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5911: dup2_x1        
        //  5912: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedFramebufferTexture1DEXT:J
        //  5915: lconst_0       
        //  5916: lcmp           
        //  5917: ifeq            5924
        //  5920: iconst_1       
        //  5921: goto            5925
        //  5924: iconst_0       
        //  5925: iand           
        //  5926: aload_2        
        //  5927: ldc_w           "OpenGL30"
        //  5930: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5935: ifne            5950
        //  5938: aload_2        
        //  5939: ldc_w           "GL_EXT_framebuffer_object"
        //  5942: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5947: ifeq            5966
        //  5950: aload_0        
        //  5951: ldc_w           "glNamedFramebufferTexture2DEXT"
        //  5954: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  5957: dup2_x1        
        //  5958: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedFramebufferTexture2DEXT:J
        //  5961: lconst_0       
        //  5962: lcmp           
        //  5963: ifeq            5970
        //  5966: iconst_1       
        //  5967: goto            5971
        //  5970: iconst_0       
        //  5971: iand           
        //  5972: aload_2        
        //  5973: ldc_w           "OpenGL30"
        //  5976: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5981: ifne            5996
        //  5984: aload_2        
        //  5985: ldc_w           "GL_EXT_framebuffer_object"
        //  5988: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  5993: ifeq            6012
        //  5996: aload_0        
        //  5997: ldc_w           "glNamedFramebufferTexture3DEXT"
        //  6000: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6003: dup2_x1        
        //  6004: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedFramebufferTexture3DEXT:J
        //  6007: lconst_0       
        //  6008: lcmp           
        //  6009: ifeq            6016
        //  6012: iconst_1       
        //  6013: goto            6017
        //  6016: iconst_0       
        //  6017: iand           
        //  6018: aload_2        
        //  6019: ldc_w           "OpenGL30"
        //  6022: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6027: ifne            6042
        //  6030: aload_2        
        //  6031: ldc_w           "GL_EXT_framebuffer_object"
        //  6034: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6039: ifeq            6058
        //  6042: aload_0        
        //  6043: ldc_w           "glNamedFramebufferRenderbufferEXT"
        //  6046: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6049: dup2_x1        
        //  6050: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedFramebufferRenderbufferEXT:J
        //  6053: lconst_0       
        //  6054: lcmp           
        //  6055: ifeq            6062
        //  6058: iconst_1       
        //  6059: goto            6063
        //  6062: iconst_0       
        //  6063: iand           
        //  6064: aload_2        
        //  6065: ldc_w           "OpenGL30"
        //  6068: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6073: ifne            6088
        //  6076: aload_2        
        //  6077: ldc_w           "GL_EXT_framebuffer_object"
        //  6080: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6085: ifeq            6104
        //  6088: aload_0        
        //  6089: ldc_w           "glGetNamedFramebufferAttachmentParameterivEXT"
        //  6092: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6095: dup2_x1        
        //  6096: putfield        org/lwjgl/opengl/ContextCapabilities.glGetNamedFramebufferAttachmentParameterivEXT:J
        //  6099: lconst_0       
        //  6100: lcmp           
        //  6101: ifeq            6108
        //  6104: iconst_1       
        //  6105: goto            6109
        //  6108: iconst_0       
        //  6109: iand           
        //  6110: aload_2        
        //  6111: ldc_w           "OpenGL30"
        //  6114: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6119: ifne            6134
        //  6122: aload_2        
        //  6123: ldc_w           "GL_EXT_framebuffer_object"
        //  6126: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6131: ifeq            6150
        //  6134: aload_0        
        //  6135: ldc_w           "glGenerateTextureMipmapEXT"
        //  6138: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6141: dup2_x1        
        //  6142: putfield        org/lwjgl/opengl/ContextCapabilities.glGenerateTextureMipmapEXT:J
        //  6145: lconst_0       
        //  6146: lcmp           
        //  6147: ifeq            6154
        //  6150: iconst_1       
        //  6151: goto            6155
        //  6154: iconst_0       
        //  6155: iand           
        //  6156: aload_2        
        //  6157: ldc_w           "OpenGL30"
        //  6160: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6165: ifne            6180
        //  6168: aload_2        
        //  6169: ldc_w           "GL_EXT_framebuffer_object"
        //  6172: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6177: ifeq            6196
        //  6180: aload_0        
        //  6181: ldc_w           "glGenerateMultiTexMipmapEXT"
        //  6184: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6187: dup2_x1        
        //  6188: putfield        org/lwjgl/opengl/ContextCapabilities.glGenerateMultiTexMipmapEXT:J
        //  6191: lconst_0       
        //  6192: lcmp           
        //  6193: ifeq            6200
        //  6196: iconst_1       
        //  6197: goto            6201
        //  6200: iconst_0       
        //  6201: iand           
        //  6202: aload_2        
        //  6203: ldc_w           "OpenGL30"
        //  6206: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6211: ifne            6226
        //  6214: aload_2        
        //  6215: ldc_w           "GL_EXT_framebuffer_object"
        //  6218: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6223: ifeq            6242
        //  6226: aload_0        
        //  6227: ldc_w           "glFramebufferDrawBufferEXT"
        //  6230: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6233: dup2_x1        
        //  6234: putfield        org/lwjgl/opengl/ContextCapabilities.glFramebufferDrawBufferEXT:J
        //  6237: lconst_0       
        //  6238: lcmp           
        //  6239: ifeq            6246
        //  6242: iconst_1       
        //  6243: goto            6247
        //  6246: iconst_0       
        //  6247: iand           
        //  6248: aload_2        
        //  6249: ldc_w           "OpenGL30"
        //  6252: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6257: ifne            6272
        //  6260: aload_2        
        //  6261: ldc_w           "GL_EXT_framebuffer_object"
        //  6264: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6269: ifeq            6288
        //  6272: aload_0        
        //  6273: ldc_w           "glFramebufferDrawBuffersEXT"
        //  6276: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6279: dup2_x1        
        //  6280: putfield        org/lwjgl/opengl/ContextCapabilities.glFramebufferDrawBuffersEXT:J
        //  6283: lconst_0       
        //  6284: lcmp           
        //  6285: ifeq            6292
        //  6288: iconst_1       
        //  6289: goto            6293
        //  6292: iconst_0       
        //  6293: iand           
        //  6294: aload_2        
        //  6295: ldc_w           "OpenGL30"
        //  6298: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6303: ifne            6318
        //  6306: aload_2        
        //  6307: ldc_w           "GL_EXT_framebuffer_object"
        //  6310: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6315: ifeq            6334
        //  6318: aload_0        
        //  6319: ldc_w           "glFramebufferReadBufferEXT"
        //  6322: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6325: dup2_x1        
        //  6326: putfield        org/lwjgl/opengl/ContextCapabilities.glFramebufferReadBufferEXT:J
        //  6329: lconst_0       
        //  6330: lcmp           
        //  6331: ifeq            6338
        //  6334: iconst_1       
        //  6335: goto            6339
        //  6338: iconst_0       
        //  6339: iand           
        //  6340: aload_2        
        //  6341: ldc_w           "OpenGL30"
        //  6344: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6349: ifne            6364
        //  6352: aload_2        
        //  6353: ldc_w           "GL_EXT_framebuffer_object"
        //  6356: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6361: ifeq            6380
        //  6364: aload_0        
        //  6365: ldc_w           "glGetFramebufferParameterivEXT"
        //  6368: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6371: dup2_x1        
        //  6372: putfield        org/lwjgl/opengl/ContextCapabilities.glGetFramebufferParameterivEXT:J
        //  6375: lconst_0       
        //  6376: lcmp           
        //  6377: ifeq            6384
        //  6380: iconst_1       
        //  6381: goto            6385
        //  6384: iconst_0       
        //  6385: iand           
        //  6386: aload_2        
        //  6387: ldc_w           "OpenGL31"
        //  6390: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6395: ifne            6410
        //  6398: aload_2        
        //  6399: ldc_w           "GL_ARB_copy_buffer"
        //  6402: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6407: ifeq            6426
        //  6410: aload_0        
        //  6411: ldc_w           "glNamedCopyBufferSubDataEXT"
        //  6414: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6417: dup2_x1        
        //  6418: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedCopyBufferSubDataEXT:J
        //  6421: lconst_0       
        //  6422: lcmp           
        //  6423: ifeq            6430
        //  6426: iconst_1       
        //  6427: goto            6431
        //  6430: iconst_0       
        //  6431: iand           
        //  6432: aload_2        
        //  6433: ldc_w           "GL_EXT_geometry_shader4"
        //  6436: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6441: ifne            6456
        //  6444: aload_2        
        //  6445: ldc_w           "GL_NV_geometry_program4"
        //  6448: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6453: ifeq            6472
        //  6456: aload_0        
        //  6457: ldc_w           "glNamedFramebufferTextureEXT"
        //  6460: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6463: dup2_x1        
        //  6464: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedFramebufferTextureEXT:J
        //  6467: lconst_0       
        //  6468: lcmp           
        //  6469: ifeq            6476
        //  6472: iconst_1       
        //  6473: goto            6477
        //  6476: iconst_0       
        //  6477: iand           
        //  6478: aload_2        
        //  6479: ldc_w           "GL_EXT_geometry_shader4"
        //  6482: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6487: ifne            6502
        //  6490: aload_2        
        //  6491: ldc_w           "GL_NV_geometry_program4"
        //  6494: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6499: ifeq            6518
        //  6502: aload_0        
        //  6503: ldc_w           "glNamedFramebufferTextureLayerEXT"
        //  6506: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6509: dup2_x1        
        //  6510: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedFramebufferTextureLayerEXT:J
        //  6513: lconst_0       
        //  6514: lcmp           
        //  6515: ifeq            6522
        //  6518: iconst_1       
        //  6519: goto            6523
        //  6522: iconst_0       
        //  6523: iand           
        //  6524: aload_2        
        //  6525: ldc_w           "GL_EXT_geometry_shader4"
        //  6528: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6533: ifne            6548
        //  6536: aload_2        
        //  6537: ldc_w           "GL_NV_geometry_program4"
        //  6540: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6545: ifeq            6564
        //  6548: aload_0        
        //  6549: ldc_w           "glNamedFramebufferTextureFaceEXT"
        //  6552: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6555: dup2_x1        
        //  6556: putfield        org/lwjgl/opengl/ContextCapabilities.glNamedFramebufferTextureFaceEXT:J
        //  6559: lconst_0       
        //  6560: lcmp           
        //  6561: ifeq            6568
        //  6564: iconst_1       
        //  6565: goto            6569
        //  6568: iconst_0       
        //  6569: iand           
        //  6570: aload_2        
        //  6571: ldc_w           "GL_NV_explicit_multisample"
        //  6574: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6579: ifeq            6598
        //  6582: aload_0        
        //  6583: ldc_w           "glTextureRenderbufferEXT"
        //  6586: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6589: dup2_x1        
        //  6590: putfield        org/lwjgl/opengl/ContextCapabilities.glTextureRenderbufferEXT:J
        //  6593: lconst_0       
        //  6594: lcmp           
        //  6595: ifeq            6602
        //  6598: iconst_1       
        //  6599: goto            6603
        //  6602: iconst_0       
        //  6603: iand           
        //  6604: aload_2        
        //  6605: ldc_w           "GL_NV_explicit_multisample"
        //  6608: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6613: ifeq            6632
        //  6616: aload_0        
        //  6617: ldc_w           "glMultiTexRenderbufferEXT"
        //  6620: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6623: dup2_x1        
        //  6624: putfield        org/lwjgl/opengl/ContextCapabilities.glMultiTexRenderbufferEXT:J
        //  6627: lconst_0       
        //  6628: lcmp           
        //  6629: ifeq            6636
        //  6632: iconst_1       
        //  6633: goto            6637
        //  6636: iconst_0       
        //  6637: iand           
        //  6638: iload_1        
        //  6639: ifne            6670
        //  6642: aload_2        
        //  6643: ldc_w           "OpenGL30"
        //  6646: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6651: ifeq            6670
        //  6654: aload_0        
        //  6655: ldc_w           "glVertexArrayVertexOffsetEXT"
        //  6658: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6661: dup2_x1        
        //  6662: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayVertexOffsetEXT:J
        //  6665: lconst_0       
        //  6666: lcmp           
        //  6667: ifeq            6674
        //  6670: iconst_1       
        //  6671: goto            6675
        //  6674: iconst_0       
        //  6675: iand           
        //  6676: iload_1        
        //  6677: ifne            6708
        //  6680: aload_2        
        //  6681: ldc_w           "OpenGL30"
        //  6684: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6689: ifeq            6708
        //  6692: aload_0        
        //  6693: ldc_w           "glVertexArrayColorOffsetEXT"
        //  6696: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6699: dup2_x1        
        //  6700: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayColorOffsetEXT:J
        //  6703: lconst_0       
        //  6704: lcmp           
        //  6705: ifeq            6712
        //  6708: iconst_1       
        //  6709: goto            6713
        //  6712: iconst_0       
        //  6713: iand           
        //  6714: iload_1        
        //  6715: ifne            6746
        //  6718: aload_2        
        //  6719: ldc_w           "OpenGL30"
        //  6722: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6727: ifeq            6746
        //  6730: aload_0        
        //  6731: ldc_w           "glVertexArrayEdgeFlagOffsetEXT"
        //  6734: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6737: dup2_x1        
        //  6738: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayEdgeFlagOffsetEXT:J
        //  6741: lconst_0       
        //  6742: lcmp           
        //  6743: ifeq            6750
        //  6746: iconst_1       
        //  6747: goto            6751
        //  6750: iconst_0       
        //  6751: iand           
        //  6752: aload_2        
        //  6753: ldc_w           "OpenGL30"
        //  6756: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6761: ifeq            6780
        //  6764: aload_0        
        //  6765: ldc_w           "glVertexArrayIndexOffsetEXT"
        //  6768: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6771: dup2_x1        
        //  6772: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayIndexOffsetEXT:J
        //  6775: lconst_0       
        //  6776: lcmp           
        //  6777: ifeq            6784
        //  6780: iconst_1       
        //  6781: goto            6785
        //  6784: iconst_0       
        //  6785: iand           
        //  6786: iload_1        
        //  6787: ifne            6818
        //  6790: aload_2        
        //  6791: ldc_w           "OpenGL30"
        //  6794: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6799: ifeq            6818
        //  6802: aload_0        
        //  6803: ldc_w           "glVertexArrayNormalOffsetEXT"
        //  6806: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6809: dup2_x1        
        //  6810: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayNormalOffsetEXT:J
        //  6813: lconst_0       
        //  6814: lcmp           
        //  6815: ifeq            6822
        //  6818: iconst_1       
        //  6819: goto            6823
        //  6822: iconst_0       
        //  6823: iand           
        //  6824: iload_1        
        //  6825: ifne            6856
        //  6828: aload_2        
        //  6829: ldc_w           "OpenGL30"
        //  6832: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6837: ifeq            6856
        //  6840: aload_0        
        //  6841: ldc_w           "glVertexArrayTexCoordOffsetEXT"
        //  6844: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6847: dup2_x1        
        //  6848: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayTexCoordOffsetEXT:J
        //  6851: lconst_0       
        //  6852: lcmp           
        //  6853: ifeq            6860
        //  6856: iconst_1       
        //  6857: goto            6861
        //  6860: iconst_0       
        //  6861: iand           
        //  6862: iload_1        
        //  6863: ifne            6894
        //  6866: aload_2        
        //  6867: ldc_w           "OpenGL30"
        //  6870: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6875: ifeq            6894
        //  6878: aload_0        
        //  6879: ldc_w           "glVertexArrayMultiTexCoordOffsetEXT"
        //  6882: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6885: dup2_x1        
        //  6886: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayMultiTexCoordOffsetEXT:J
        //  6889: lconst_0       
        //  6890: lcmp           
        //  6891: ifeq            6898
        //  6894: iconst_1       
        //  6895: goto            6899
        //  6898: iconst_0       
        //  6899: iand           
        //  6900: iload_1        
        //  6901: ifne            6932
        //  6904: aload_2        
        //  6905: ldc_w           "OpenGL30"
        //  6908: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6913: ifeq            6932
        //  6916: aload_0        
        //  6917: ldc_w           "glVertexArrayFogCoordOffsetEXT"
        //  6920: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6923: dup2_x1        
        //  6924: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayFogCoordOffsetEXT:J
        //  6927: lconst_0       
        //  6928: lcmp           
        //  6929: ifeq            6936
        //  6932: iconst_1       
        //  6933: goto            6937
        //  6936: iconst_0       
        //  6937: iand           
        //  6938: iload_1        
        //  6939: ifne            6970
        //  6942: aload_2        
        //  6943: ldc_w           "OpenGL30"
        //  6946: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6951: ifeq            6970
        //  6954: aload_0        
        //  6955: ldc_w           "glVertexArraySecondaryColorOffsetEXT"
        //  6958: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6961: dup2_x1        
        //  6962: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArraySecondaryColorOffsetEXT:J
        //  6965: lconst_0       
        //  6966: lcmp           
        //  6967: ifeq            6974
        //  6970: iconst_1       
        //  6971: goto            6975
        //  6974: iconst_0       
        //  6975: iand           
        //  6976: aload_2        
        //  6977: ldc_w           "OpenGL30"
        //  6980: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  6985: ifeq            7004
        //  6988: aload_0        
        //  6989: ldc_w           "glVertexArrayVertexAttribOffsetEXT"
        //  6992: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  6995: dup2_x1        
        //  6996: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayVertexAttribOffsetEXT:J
        //  6999: lconst_0       
        //  7000: lcmp           
        //  7001: ifeq            7008
        //  7004: iconst_1       
        //  7005: goto            7009
        //  7008: iconst_0       
        //  7009: iand           
        //  7010: aload_2        
        //  7011: ldc_w           "OpenGL30"
        //  7014: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7019: ifeq            7038
        //  7022: aload_0        
        //  7023: ldc_w           "glVertexArrayVertexAttribIOffsetEXT"
        //  7026: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7029: dup2_x1        
        //  7030: putfield        org/lwjgl/opengl/ContextCapabilities.glVertexArrayVertexAttribIOffsetEXT:J
        //  7033: lconst_0       
        //  7034: lcmp           
        //  7035: ifeq            7042
        //  7038: iconst_1       
        //  7039: goto            7043
        //  7042: iconst_0       
        //  7043: iand           
        //  7044: aload_2        
        //  7045: ldc_w           "OpenGL30"
        //  7048: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7053: ifeq            7072
        //  7056: aload_0        
        //  7057: ldc_w           "glEnableVertexArrayEXT"
        //  7060: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7063: dup2_x1        
        //  7064: putfield        org/lwjgl/opengl/ContextCapabilities.glEnableVertexArrayEXT:J
        //  7067: lconst_0       
        //  7068: lcmp           
        //  7069: ifeq            7076
        //  7072: iconst_1       
        //  7073: goto            7077
        //  7076: iconst_0       
        //  7077: iand           
        //  7078: aload_2        
        //  7079: ldc_w           "OpenGL30"
        //  7082: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7087: ifeq            7106
        //  7090: aload_0        
        //  7091: ldc_w           "glDisableVertexArrayEXT"
        //  7094: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7097: dup2_x1        
        //  7098: putfield        org/lwjgl/opengl/ContextCapabilities.glDisableVertexArrayEXT:J
        //  7101: lconst_0       
        //  7102: lcmp           
        //  7103: ifeq            7110
        //  7106: iconst_1       
        //  7107: goto            7111
        //  7110: iconst_0       
        //  7111: iand           
        //  7112: aload_2        
        //  7113: ldc_w           "OpenGL30"
        //  7116: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7121: ifeq            7140
        //  7124: aload_0        
        //  7125: ldc_w           "glEnableVertexArrayAttribEXT"
        //  7128: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7131: dup2_x1        
        //  7132: putfield        org/lwjgl/opengl/ContextCapabilities.glEnableVertexArrayAttribEXT:J
        //  7135: lconst_0       
        //  7136: lcmp           
        //  7137: ifeq            7144
        //  7140: iconst_1       
        //  7141: goto            7145
        //  7144: iconst_0       
        //  7145: iand           
        //  7146: aload_2        
        //  7147: ldc_w           "OpenGL30"
        //  7150: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7155: ifeq            7174
        //  7158: aload_0        
        //  7159: ldc_w           "glDisableVertexArrayAttribEXT"
        //  7162: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7165: dup2_x1        
        //  7166: putfield        org/lwjgl/opengl/ContextCapabilities.glDisableVertexArrayAttribEXT:J
        //  7169: lconst_0       
        //  7170: lcmp           
        //  7171: ifeq            7178
        //  7174: iconst_1       
        //  7175: goto            7179
        //  7178: iconst_0       
        //  7179: iand           
        //  7180: aload_2        
        //  7181: ldc_w           "OpenGL30"
        //  7184: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7189: ifeq            7208
        //  7192: aload_0        
        //  7193: ldc_w           "glGetVertexArrayIntegervEXT"
        //  7196: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7199: dup2_x1        
        //  7200: putfield        org/lwjgl/opengl/ContextCapabilities.glGetVertexArrayIntegervEXT:J
        //  7203: lconst_0       
        //  7204: lcmp           
        //  7205: ifeq            7212
        //  7208: iconst_1       
        //  7209: goto            7213
        //  7212: iconst_0       
        //  7213: iand           
        //  7214: aload_2        
        //  7215: ldc_w           "OpenGL30"
        //  7218: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7223: ifeq            7242
        //  7226: aload_0        
        //  7227: ldc_w           "glGetVertexArrayPointervEXT"
        //  7230: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7233: dup2_x1        
        //  7234: putfield        org/lwjgl/opengl/ContextCapabilities.glGetVertexArrayPointervEXT:J
        //  7237: lconst_0       
        //  7238: lcmp           
        //  7239: ifeq            7246
        //  7242: iconst_1       
        //  7243: goto            7247
        //  7246: iconst_0       
        //  7247: iand           
        //  7248: aload_2        
        //  7249: ldc_w           "OpenGL30"
        //  7252: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7257: ifeq            7276
        //  7260: aload_0        
        //  7261: ldc_w           "glGetVertexArrayIntegeri_vEXT"
        //  7264: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7267: dup2_x1        
        //  7268: putfield        org/lwjgl/opengl/ContextCapabilities.glGetVertexArrayIntegeri_vEXT:J
        //  7271: lconst_0       
        //  7272: lcmp           
        //  7273: ifeq            7280
        //  7276: iconst_1       
        //  7277: goto            7281
        //  7280: iconst_0       
        //  7281: iand           
        //  7282: aload_2        
        //  7283: ldc_w           "OpenGL30"
        //  7286: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7291: ifeq            7310
        //  7294: aload_0        
        //  7295: ldc_w           "glGetVertexArrayPointeri_vEXT"
        //  7298: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7301: dup2_x1        
        //  7302: putfield        org/lwjgl/opengl/ContextCapabilities.glGetVertexArrayPointeri_vEXT:J
        //  7305: lconst_0       
        //  7306: lcmp           
        //  7307: ifeq            7314
        //  7310: iconst_1       
        //  7311: goto            7315
        //  7314: iconst_0       
        //  7315: iand           
        //  7316: aload_2        
        //  7317: ldc_w           "OpenGL30"
        //  7320: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7325: ifeq            7344
        //  7328: aload_0        
        //  7329: ldc_w           "glMapNamedBufferRangeEXT"
        //  7332: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7335: dup2_x1        
        //  7336: putfield        org/lwjgl/opengl/ContextCapabilities.glMapNamedBufferRangeEXT:J
        //  7339: lconst_0       
        //  7340: lcmp           
        //  7341: ifeq            7348
        //  7344: iconst_1       
        //  7345: goto            7349
        //  7348: iconst_0       
        //  7349: iand           
        //  7350: aload_2        
        //  7351: ldc_w           "OpenGL30"
        //  7354: invokeinterface java/util/Set.contains:(Ljava/lang/Object;)Z
        //  7359: ifeq            7378
        //  7362: aload_0        
        //  7363: ldc_w           "glFlushMappedNamedBufferRangeEXT"
        //  7366: invokestatic    org/lwjgl/opengl/GLContext.getFunctionAddress:(Ljava/lang/String;)J
        //  7369: dup2_x1        
        //  7370: putfield        org/lwjgl/opengl/ContextCapabilities.glFlushMappedNamedBufferRangeEXT:J
        //  7373: lconst_0       
        //  7374: lcmp           
        //  7375: ifeq            7382
        //  7378: iconst_1       
        //  7379: goto            7383
        //  7382: iconst_0       
        //  7383: iand           
        //  7384: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #2550 (coming from #2549).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private boolean EXT_draw_buffers2_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glColorMaskIndexedEXT");
        this.glColorMaskIndexedEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetBooleanIndexedvEXT");
        this.glGetBooleanIndexedvEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetIntegerIndexedvEXT");
        this.glGetIntegerIndexedvEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glEnableIndexedEXT");
        this.glEnableIndexedEXT = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glDisableIndexedEXT");
        this.glDisableIndexedEXT = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glIsEnabledIndexedEXT");
        this.glIsEnabledIndexedEXT = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean EXT_draw_instanced_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawArraysInstancedEXT");
        this.glDrawArraysInstancedEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawElementsInstancedEXT");
        this.glDrawElementsInstancedEXT = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean EXT_draw_range_elements_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawRangeElementsEXT");
        this.glDrawRangeElementsEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_fog_coord_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glFogCoordfEXT");
        this.glFogCoordfEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glFogCoorddEXT");
        this.glFogCoorddEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glFogCoordPointerEXT");
        this.glFogCoordPointerEXT = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean EXT_framebuffer_blit_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBlitFramebufferEXT");
        this.glBlitFramebufferEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_framebuffer_multisample_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glRenderbufferStorageMultisampleEXT");
        this.glRenderbufferStorageMultisampleEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_framebuffer_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glIsRenderbufferEXT");
        this.glIsRenderbufferEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBindRenderbufferEXT");
        this.glBindRenderbufferEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDeleteRenderbuffersEXT");
        this.glDeleteRenderbuffersEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGenRenderbuffersEXT");
        this.glGenRenderbuffersEXT = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glRenderbufferStorageEXT");
        this.glRenderbufferStorageEXT = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetRenderbufferParameterivEXT");
        this.glGetRenderbufferParameterivEXT = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glIsFramebufferEXT");
        this.glIsFramebufferEXT = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glBindFramebufferEXT");
        this.glBindFramebufferEXT = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glDeleteFramebuffersEXT");
        this.glDeleteFramebuffersEXT = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glGenFramebuffersEXT");
        this.glGenFramebuffersEXT = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glCheckFramebufferStatusEXT");
        this.glCheckFramebufferStatusEXT = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glFramebufferTexture1DEXT");
        this.glFramebufferTexture1DEXT = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glFramebufferTexture2DEXT");
        this.glFramebufferTexture2DEXT = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glFramebufferTexture3DEXT");
        this.glFramebufferTexture3DEXT = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glFramebufferRenderbufferEXT");
        this.glFramebufferRenderbufferEXT = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glGetFramebufferAttachmentParameterivEXT");
        this.glGetFramebufferAttachmentParameterivEXT = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glGenerateMipmapEXT");
        this.glGenerateMipmapEXT = functionAddress17;
        return b16 & functionAddress17 != 0L;
    }
    
    private boolean EXT_geometry_shader4_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProgramParameteriEXT");
        this.glProgramParameteriEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glFramebufferTextureEXT");
        this.glFramebufferTextureEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glFramebufferTextureLayerEXT");
        this.glFramebufferTextureLayerEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glFramebufferTextureFaceEXT");
        this.glFramebufferTextureFaceEXT = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean EXT_gpu_program_parameters_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProgramEnvParameters4fvEXT");
        this.glProgramEnvParameters4fvEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glProgramLocalParameters4fvEXT");
        this.glProgramLocalParameters4fvEXT = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean EXT_gpu_shader4_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexAttribI1iEXT");
        this.glVertexAttribI1iEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexAttribI2iEXT");
        this.glVertexAttribI2iEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexAttribI3iEXT");
        this.glVertexAttribI3iEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glVertexAttribI4iEXT");
        this.glVertexAttribI4iEXT = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glVertexAttribI1uiEXT");
        this.glVertexAttribI1uiEXT = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexAttribI2uiEXT");
        this.glVertexAttribI2uiEXT = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glVertexAttribI3uiEXT");
        this.glVertexAttribI3uiEXT = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glVertexAttribI4uiEXT");
        this.glVertexAttribI4uiEXT = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glVertexAttribI1ivEXT");
        this.glVertexAttribI1ivEXT = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVertexAttribI2ivEXT");
        this.glVertexAttribI2ivEXT = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glVertexAttribI3ivEXT");
        this.glVertexAttribI3ivEXT = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glVertexAttribI4ivEXT");
        this.glVertexAttribI4ivEXT = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glVertexAttribI1uivEXT");
        this.glVertexAttribI1uivEXT = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glVertexAttribI2uivEXT");
        this.glVertexAttribI2uivEXT = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glVertexAttribI3uivEXT");
        this.glVertexAttribI3uivEXT = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glVertexAttribI4uivEXT");
        this.glVertexAttribI4uivEXT = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glVertexAttribI4bvEXT");
        this.glVertexAttribI4bvEXT = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glVertexAttribI4svEXT");
        this.glVertexAttribI4svEXT = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glVertexAttribI4ubvEXT");
        this.glVertexAttribI4ubvEXT = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glVertexAttribI4usvEXT");
        this.glVertexAttribI4usvEXT = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glVertexAttribIPointerEXT");
        this.glVertexAttribIPointerEXT = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glGetVertexAttribIivEXT");
        this.glGetVertexAttribIivEXT = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glGetVertexAttribIuivEXT");
        this.glGetVertexAttribIuivEXT = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glUniform1uiEXT");
        this.glUniform1uiEXT = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glUniform2uiEXT");
        this.glUniform2uiEXT = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glUniform3uiEXT");
        this.glUniform3uiEXT = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glUniform4uiEXT");
        this.glUniform4uiEXT = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glUniform1uivEXT");
        this.glUniform1uivEXT = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glUniform2uivEXT");
        this.glUniform2uivEXT = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glUniform3uivEXT");
        this.glUniform3uivEXT = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glUniform4uivEXT");
        this.glUniform4uivEXT = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glGetUniformuivEXT");
        this.glGetUniformuivEXT = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glBindFragDataLocationEXT");
        this.glBindFragDataLocationEXT = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glGetFragDataLocationEXT");
        this.glGetFragDataLocationEXT = functionAddress34;
        return b33 & functionAddress34 != 0L;
    }
    
    private boolean EXT_multi_draw_arrays_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMultiDrawArraysEXT");
        this.glMultiDrawArraysEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_paletted_texture_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glColorTableEXT");
        this.glColorTableEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glColorSubTableEXT");
        this.glColorSubTableEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetColorTableEXT");
        this.glGetColorTableEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetColorTableParameterivEXT");
        this.glGetColorTableParameterivEXT = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetColorTableParameterfvEXT");
        this.glGetColorTableParameterfvEXT = functionAddress5;
        return b4 & functionAddress5 != 0L;
    }
    
    private boolean EXT_point_parameters_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glPointParameterfEXT");
        this.glPointParameterfEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glPointParameterfvEXT");
        this.glPointParameterfvEXT = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean EXT_provoking_vertex_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProvokingVertexEXT");
        this.glProvokingVertexEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_secondary_color_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glSecondaryColor3bEXT");
        this.glSecondaryColor3bEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glSecondaryColor3fEXT");
        this.glSecondaryColor3fEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glSecondaryColor3dEXT");
        this.glSecondaryColor3dEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glSecondaryColor3ubEXT");
        this.glSecondaryColor3ubEXT = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glSecondaryColorPointerEXT");
        this.glSecondaryColorPointerEXT = functionAddress5;
        return b4 & functionAddress5 != 0L;
    }
    
    private boolean EXT_separate_shader_objects_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glUseShaderProgramEXT");
        this.glUseShaderProgramEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glActiveProgramEXT");
        this.glActiveProgramEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glCreateShaderProgramEXT");
        this.glCreateShaderProgramEXT = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean EXT_shader_image_load_store_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindImageTextureEXT");
        this.glBindImageTextureEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMemoryBarrierEXT");
        this.glMemoryBarrierEXT = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean EXT_stencil_clear_tag_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glStencilClearTagEXT");
        this.glStencilClearTagEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_stencil_two_side_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glActiveStencilFaceEXT");
        this.glActiveStencilFaceEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_texture_array_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glFramebufferTextureLayerEXT");
        this.glFramebufferTextureLayerEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_texture_buffer_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTexBufferEXT");
        this.glTexBufferEXT = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean EXT_texture_integer_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glClearColorIiEXT");
        this.glClearColorIiEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glClearColorIuiEXT");
        this.glClearColorIuiEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glTexParameterIivEXT");
        this.glTexParameterIivEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glTexParameterIuivEXT");
        this.glTexParameterIuivEXT = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetTexParameterIivEXT");
        this.glGetTexParameterIivEXT = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetTexParameterIuivEXT");
        this.glGetTexParameterIuivEXT = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean EXT_timer_query_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetQueryObjecti64vEXT");
        this.glGetQueryObjecti64vEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetQueryObjectui64vEXT");
        this.glGetQueryObjectui64vEXT = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean EXT_transform_feedback_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindBufferRangeEXT");
        this.glBindBufferRangeEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBindBufferOffsetEXT");
        this.glBindBufferOffsetEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glBindBufferBaseEXT");
        this.glBindBufferBaseEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBeginTransformFeedbackEXT");
        this.glBeginTransformFeedbackEXT = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glEndTransformFeedbackEXT");
        this.glEndTransformFeedbackEXT = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glTransformFeedbackVaryingsEXT");
        this.glTransformFeedbackVaryingsEXT = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetTransformFeedbackVaryingEXT");
        this.glGetTransformFeedbackVaryingEXT = functionAddress7;
        return b6 & functionAddress7 != 0L;
    }
    
    private boolean EXT_vertex_attrib_64bit_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glVertexAttribL1dEXT");
        this.glVertexAttribL1dEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexAttribL2dEXT");
        this.glVertexAttribL2dEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexAttribL3dEXT");
        this.glVertexAttribL3dEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glVertexAttribL4dEXT");
        this.glVertexAttribL4dEXT = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glVertexAttribL1dvEXT");
        this.glVertexAttribL1dvEXT = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexAttribL2dvEXT");
        this.glVertexAttribL2dvEXT = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glVertexAttribL3dvEXT");
        this.glVertexAttribL3dvEXT = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glVertexAttribL4dvEXT");
        this.glVertexAttribL4dvEXT = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glVertexAttribLPointerEXT");
        this.glVertexAttribLPointerEXT = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glGetVertexAttribLdvEXT");
        this.glGetVertexAttribLdvEXT = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress11 = GLContext.getFunctionAddress("glVertexArrayVertexAttribLOffsetEXT");
            this.glVertexArrayVertexAttribLOffsetEXT = functionAddress11;
            if (functionAddress11 == 0L) {
                final boolean b11 = false;
                return b10 & b11;
            }
        }
        final boolean b11 = true;
        return b10 & b11;
    }
    
    private boolean EXT_vertex_shader_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBeginVertexShaderEXT");
        this.glBeginVertexShaderEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glEndVertexShaderEXT");
        this.glEndVertexShaderEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glBindVertexShaderEXT");
        this.glBindVertexShaderEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGenVertexShadersEXT");
        this.glGenVertexShadersEXT = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glDeleteVertexShaderEXT");
        this.glDeleteVertexShaderEXT = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glShaderOp1EXT");
        this.glShaderOp1EXT = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glShaderOp2EXT");
        this.glShaderOp2EXT = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glShaderOp3EXT");
        this.glShaderOp3EXT = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glSwizzleEXT");
        this.glSwizzleEXT = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glWriteMaskEXT");
        this.glWriteMaskEXT = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glInsertComponentEXT");
        this.glInsertComponentEXT = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glExtractComponentEXT");
        this.glExtractComponentEXT = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glGenSymbolsEXT");
        this.glGenSymbolsEXT = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glSetInvariantEXT");
        this.glSetInvariantEXT = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glSetLocalConstantEXT");
        this.glSetLocalConstantEXT = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glVariantbvEXT");
        this.glVariantbvEXT = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glVariantsvEXT");
        this.glVariantsvEXT = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glVariantivEXT");
        this.glVariantivEXT = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glVariantfvEXT");
        this.glVariantfvEXT = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glVariantdvEXT");
        this.glVariantdvEXT = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glVariantubvEXT");
        this.glVariantubvEXT = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glVariantusvEXT");
        this.glVariantusvEXT = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glVariantuivEXT");
        this.glVariantuivEXT = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glVariantPointerEXT");
        this.glVariantPointerEXT = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glEnableVariantClientStateEXT");
        this.glEnableVariantClientStateEXT = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glDisableVariantClientStateEXT");
        this.glDisableVariantClientStateEXT = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glBindLightParameterEXT");
        this.glBindLightParameterEXT = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glBindMaterialParameterEXT");
        this.glBindMaterialParameterEXT = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glBindTexGenParameterEXT");
        this.glBindTexGenParameterEXT = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glBindTextureUnitParameterEXT");
        this.glBindTextureUnitParameterEXT = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glBindParameterEXT");
        this.glBindParameterEXT = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glIsVariantEnabledEXT");
        this.glIsVariantEnabledEXT = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glGetVariantBooleanvEXT");
        this.glGetVariantBooleanvEXT = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glGetVariantIntegervEXT");
        this.glGetVariantIntegervEXT = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glGetVariantFloatvEXT");
        this.glGetVariantFloatvEXT = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glGetVariantPointervEXT");
        this.glGetVariantPointervEXT = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glGetInvariantBooleanvEXT");
        this.glGetInvariantBooleanvEXT = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glGetInvariantIntegervEXT");
        this.glGetInvariantIntegervEXT = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glGetInvariantFloatvEXT");
        this.glGetInvariantFloatvEXT = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glGetLocalConstantBooleanvEXT");
        this.glGetLocalConstantBooleanvEXT = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glGetLocalConstantIntegervEXT");
        this.glGetLocalConstantIntegervEXT = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glGetLocalConstantFloatvEXT");
        this.glGetLocalConstantFloatvEXT = functionAddress42;
        return b41 & functionAddress42 != 0L;
    }
    
    private boolean EXT_vertex_weighting_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexWeightfEXT");
        this.glVertexWeightfEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexWeightPointerEXT");
        this.glVertexWeightPointerEXT = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean GL11_initNativeFunctionAddresses(final boolean b) {
        boolean b2 = false;
        Label_0025: {
            if (!b) {
                final long functionAddress = GLContext.getFunctionAddress("glAccum");
                this.glAccum = functionAddress;
                if (functionAddress == 0L) {
                    b2 = false;
                    break Label_0025;
                }
            }
            b2 = true;
        }
        boolean b3 = false;
        Label_0050: {
            if (!b) {
                final long functionAddress2 = GLContext.getFunctionAddress("glAlphaFunc");
                this.glAlphaFunc = functionAddress2;
                if (functionAddress2 == 0L) {
                    b3 = false;
                    break Label_0050;
                }
            }
            b3 = true;
        }
        final boolean b4 = b2 & b3;
        final long functionAddress3 = GLContext.getFunctionAddress("glClearColor");
        this.glClearColor = functionAddress3;
        final boolean b5 = b4 & functionAddress3 != 0L;
        boolean b6 = false;
        Label_0098: {
            if (!b) {
                final long functionAddress4 = GLContext.getFunctionAddress("glClearAccum");
                this.glClearAccum = functionAddress4;
                if (functionAddress4 == 0L) {
                    b6 = false;
                    break Label_0098;
                }
            }
            b6 = true;
        }
        final boolean b7 = b5 & b6;
        final long functionAddress5 = GLContext.getFunctionAddress("glClear");
        this.glClear = functionAddress5;
        final boolean b8 = b7 & functionAddress5 != 0L;
        boolean b9 = false;
        Label_0146: {
            if (!b) {
                final long functionAddress6 = GLContext.getFunctionAddress("glCallLists");
                this.glCallLists = functionAddress6;
                if (functionAddress6 == 0L) {
                    b9 = false;
                    break Label_0146;
                }
            }
            b9 = true;
        }
        final boolean b10 = b8 & b9;
        boolean b11 = false;
        Label_0172: {
            if (!b) {
                final long functionAddress7 = GLContext.getFunctionAddress("glCallList");
                this.glCallList = functionAddress7;
                if (functionAddress7 == 0L) {
                    b11 = false;
                    break Label_0172;
                }
            }
            b11 = true;
        }
        final boolean b12 = b10 & b11;
        final long functionAddress8 = GLContext.getFunctionAddress("glBlendFunc");
        this.glBlendFunc = functionAddress8;
        final boolean b13 = b12 & functionAddress8 != 0L;
        boolean b14 = false;
        Label_0220: {
            if (!b) {
                final long functionAddress9 = GLContext.getFunctionAddress("glBitmap");
                this.glBitmap = functionAddress9;
                if (functionAddress9 == 0L) {
                    b14 = false;
                    break Label_0220;
                }
            }
            b14 = true;
        }
        final boolean b15 = b13 & b14;
        final long functionAddress10 = GLContext.getFunctionAddress("glBindTexture");
        this.glBindTexture = functionAddress10;
        final boolean b16 = b15 & functionAddress10 != 0L;
        boolean b17 = false;
        Label_0268: {
            if (!b) {
                final long functionAddress11 = GLContext.getFunctionAddress("glPrioritizeTextures");
                this.glPrioritizeTextures = functionAddress11;
                if (functionAddress11 == 0L) {
                    b17 = false;
                    break Label_0268;
                }
            }
            b17 = true;
        }
        final boolean b18 = b16 & b17;
        boolean b19 = false;
        Label_0294: {
            if (!b) {
                final long functionAddress12 = GLContext.getFunctionAddress("glAreTexturesResident");
                this.glAreTexturesResident = functionAddress12;
                if (functionAddress12 == 0L) {
                    b19 = false;
                    break Label_0294;
                }
            }
            b19 = true;
        }
        final boolean b20 = b18 & b19;
        boolean b21 = false;
        Label_0320: {
            if (!b) {
                final long functionAddress13 = GLContext.getFunctionAddress("glBegin");
                this.glBegin = functionAddress13;
                if (functionAddress13 == 0L) {
                    b21 = false;
                    break Label_0320;
                }
            }
            b21 = true;
        }
        final boolean b22 = b20 & b21;
        boolean b23 = false;
        Label_0346: {
            if (!b) {
                final long functionAddress14 = GLContext.getFunctionAddress("glEnd");
                this.glEnd = functionAddress14;
                if (functionAddress14 == 0L) {
                    b23 = false;
                    break Label_0346;
                }
            }
            b23 = true;
        }
        final boolean b24 = b22 & b23;
        final long functionAddress15 = GLContext.getFunctionAddress("glArrayElement");
        this.glArrayElement = functionAddress15;
        final boolean b25 = b24 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glClearDepth");
        this.glClearDepth = functionAddress16;
        final boolean b26 = b25 & functionAddress16 != 0L;
        boolean b27 = false;
        Label_0416: {
            if (!b) {
                final long functionAddress17 = GLContext.getFunctionAddress("glDeleteLists");
                this.glDeleteLists = functionAddress17;
                if (functionAddress17 == 0L) {
                    b27 = false;
                    break Label_0416;
                }
            }
            b27 = true;
        }
        final boolean b28 = b26 & b27;
        final long functionAddress18 = GLContext.getFunctionAddress("glDeleteTextures");
        this.glDeleteTextures = functionAddress18;
        final boolean b29 = b28 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glCullFace");
        this.glCullFace = functionAddress19;
        final boolean b30 = b29 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glCopyTexSubImage2D");
        this.glCopyTexSubImage2D = functionAddress20;
        final boolean b31 = b30 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glCopyTexSubImage1D");
        this.glCopyTexSubImage1D = functionAddress21;
        final boolean b32 = b31 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glCopyTexImage2D");
        this.glCopyTexImage2D = functionAddress22;
        final boolean b33 = b32 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glCopyTexImage1D");
        this.glCopyTexImage1D = functionAddress23;
        final boolean b34 = b33 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glCopyPixels");
        this.glCopyPixels = functionAddress24;
        final boolean b35 = b34 & functionAddress24 != 0L;
        boolean b36 = false;
        Label_0596: {
            if (!b) {
                final long functionAddress25 = GLContext.getFunctionAddress("glColorPointer");
                this.glColorPointer = functionAddress25;
                if (functionAddress25 == 0L) {
                    b36 = false;
                    break Label_0596;
                }
            }
            b36 = true;
        }
        final boolean b37 = b35 & b36;
        boolean b38 = false;
        Label_0622: {
            if (!b) {
                final long functionAddress26 = GLContext.getFunctionAddress("glColorMaterial");
                this.glColorMaterial = functionAddress26;
                if (functionAddress26 == 0L) {
                    b38 = false;
                    break Label_0622;
                }
            }
            b38 = true;
        }
        final boolean b39 = b37 & b38;
        final long functionAddress27 = GLContext.getFunctionAddress("glColorMask");
        this.glColorMask = functionAddress27;
        final boolean b40 = b39 & functionAddress27 != 0L;
        boolean b41 = false;
        Label_0670: {
            if (!b) {
                final long functionAddress28 = GLContext.getFunctionAddress("glColor3b");
                this.glColor3b = functionAddress28;
                if (functionAddress28 == 0L) {
                    b41 = false;
                    break Label_0670;
                }
            }
            b41 = true;
        }
        final boolean b42 = b40 & b41;
        boolean b43 = false;
        Label_0696: {
            if (!b) {
                final long functionAddress29 = GLContext.getFunctionAddress("glColor3f");
                this.glColor3f = functionAddress29;
                if (functionAddress29 == 0L) {
                    b43 = false;
                    break Label_0696;
                }
            }
            b43 = true;
        }
        final boolean b44 = b42 & b43;
        boolean b45 = false;
        Label_0722: {
            if (!b) {
                final long functionAddress30 = GLContext.getFunctionAddress("glColor3d");
                this.glColor3d = functionAddress30;
                if (functionAddress30 == 0L) {
                    b45 = false;
                    break Label_0722;
                }
            }
            b45 = true;
        }
        final boolean b46 = b44 & b45;
        boolean b47 = false;
        Label_0748: {
            if (!b) {
                final long functionAddress31 = GLContext.getFunctionAddress("glColor3ub");
                this.glColor3ub = functionAddress31;
                if (functionAddress31 == 0L) {
                    b47 = false;
                    break Label_0748;
                }
            }
            b47 = true;
        }
        final boolean b48 = b46 & b47;
        boolean b49 = false;
        Label_0774: {
            if (!b) {
                final long functionAddress32 = GLContext.getFunctionAddress("glColor4b");
                this.glColor4b = functionAddress32;
                if (functionAddress32 == 0L) {
                    b49 = false;
                    break Label_0774;
                }
            }
            b49 = true;
        }
        final boolean b50 = b48 & b49;
        boolean b51 = false;
        Label_0800: {
            if (!b) {
                final long functionAddress33 = GLContext.getFunctionAddress("glColor4f");
                this.glColor4f = functionAddress33;
                if (functionAddress33 == 0L) {
                    b51 = false;
                    break Label_0800;
                }
            }
            b51 = true;
        }
        final boolean b52 = b50 & b51;
        boolean b53 = false;
        Label_0826: {
            if (!b) {
                final long functionAddress34 = GLContext.getFunctionAddress("glColor4d");
                this.glColor4d = functionAddress34;
                if (functionAddress34 == 0L) {
                    b53 = false;
                    break Label_0826;
                }
            }
            b53 = true;
        }
        final boolean b54 = b52 & b53;
        boolean b55 = false;
        Label_0852: {
            if (!b) {
                final long functionAddress35 = GLContext.getFunctionAddress("glColor4ub");
                this.glColor4ub = functionAddress35;
                if (functionAddress35 == 0L) {
                    b55 = false;
                    break Label_0852;
                }
            }
            b55 = true;
        }
        final boolean b56 = b54 & b55;
        final long functionAddress36 = GLContext.getFunctionAddress("glClipPlane");
        this.glClipPlane = functionAddress36;
        final boolean b57 = b56 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glClearStencil");
        this.glClearStencil = functionAddress37;
        final boolean b58 = b57 & functionAddress37 != 0L;
        boolean b59 = false;
        Label_0922: {
            if (!b) {
                final long functionAddress38 = GLContext.getFunctionAddress("glEvalPoint1");
                this.glEvalPoint1 = functionAddress38;
                if (functionAddress38 == 0L) {
                    b59 = false;
                    break Label_0922;
                }
            }
            b59 = true;
        }
        final boolean b60 = b58 & b59;
        boolean b61 = false;
        Label_0948: {
            if (!b) {
                final long functionAddress39 = GLContext.getFunctionAddress("glEvalPoint2");
                this.glEvalPoint2 = functionAddress39;
                if (functionAddress39 == 0L) {
                    b61 = false;
                    break Label_0948;
                }
            }
            b61 = true;
        }
        final boolean b62 = b60 & b61;
        boolean b63 = false;
        Label_0974: {
            if (!b) {
                final long functionAddress40 = GLContext.getFunctionAddress("glEvalMesh1");
                this.glEvalMesh1 = functionAddress40;
                if (functionAddress40 == 0L) {
                    b63 = false;
                    break Label_0974;
                }
            }
            b63 = true;
        }
        final boolean b64 = b62 & b63;
        boolean b65 = false;
        Label_1000: {
            if (!b) {
                final long functionAddress41 = GLContext.getFunctionAddress("glEvalMesh2");
                this.glEvalMesh2 = functionAddress41;
                if (functionAddress41 == 0L) {
                    b65 = false;
                    break Label_1000;
                }
            }
            b65 = true;
        }
        final boolean b66 = b64 & b65;
        boolean b67 = false;
        Label_1026: {
            if (!b) {
                final long functionAddress42 = GLContext.getFunctionAddress("glEvalCoord1f");
                this.glEvalCoord1f = functionAddress42;
                if (functionAddress42 == 0L) {
                    b67 = false;
                    break Label_1026;
                }
            }
            b67 = true;
        }
        final boolean b68 = b66 & b67;
        boolean b69 = false;
        Label_1052: {
            if (!b) {
                final long functionAddress43 = GLContext.getFunctionAddress("glEvalCoord1d");
                this.glEvalCoord1d = functionAddress43;
                if (functionAddress43 == 0L) {
                    b69 = false;
                    break Label_1052;
                }
            }
            b69 = true;
        }
        final boolean b70 = b68 & b69;
        boolean b71 = false;
        Label_1078: {
            if (!b) {
                final long functionAddress44 = GLContext.getFunctionAddress("glEvalCoord2f");
                this.glEvalCoord2f = functionAddress44;
                if (functionAddress44 == 0L) {
                    b71 = false;
                    break Label_1078;
                }
            }
            b71 = true;
        }
        final boolean b72 = b70 & b71;
        boolean b73 = false;
        Label_1104: {
            if (!b) {
                final long functionAddress45 = GLContext.getFunctionAddress("glEvalCoord2d");
                this.glEvalCoord2d = functionAddress45;
                if (functionAddress45 == 0L) {
                    b73 = false;
                    break Label_1104;
                }
            }
            b73 = true;
        }
        final boolean b74 = b72 & b73;
        boolean b75 = false;
        Label_1130: {
            if (!b) {
                final long functionAddress46 = GLContext.getFunctionAddress("glEnableClientState");
                this.glEnableClientState = functionAddress46;
                if (functionAddress46 == 0L) {
                    b75 = false;
                    break Label_1130;
                }
            }
            b75 = true;
        }
        final boolean b76 = b74 & b75;
        boolean b77 = false;
        Label_1156: {
            if (!b) {
                final long functionAddress47 = GLContext.getFunctionAddress("glDisableClientState");
                this.glDisableClientState = functionAddress47;
                if (functionAddress47 == 0L) {
                    b77 = false;
                    break Label_1156;
                }
            }
            b77 = true;
        }
        final boolean b78 = b76 & b77;
        final long functionAddress48 = GLContext.getFunctionAddress("glEnable");
        this.glEnable = functionAddress48;
        final boolean b79 = b78 & functionAddress48 != 0L;
        final long functionAddress49 = GLContext.getFunctionAddress("glDisable");
        this.glDisable = functionAddress49;
        final boolean b80 = b79 & functionAddress49 != 0L;
        boolean b81 = false;
        Label_1226: {
            if (!b) {
                final long functionAddress50 = GLContext.getFunctionAddress("glEdgeFlagPointer");
                this.glEdgeFlagPointer = functionAddress50;
                if (functionAddress50 == 0L) {
                    b81 = false;
                    break Label_1226;
                }
            }
            b81 = true;
        }
        final boolean b82 = b80 & b81;
        boolean b83 = false;
        Label_1252: {
            if (!b) {
                final long functionAddress51 = GLContext.getFunctionAddress("glEdgeFlag");
                this.glEdgeFlag = functionAddress51;
                if (functionAddress51 == 0L) {
                    b83 = false;
                    break Label_1252;
                }
            }
            b83 = true;
        }
        final boolean b84 = b82 & b83;
        boolean b85 = false;
        Label_1278: {
            if (!b) {
                final long functionAddress52 = GLContext.getFunctionAddress("glDrawPixels");
                this.glDrawPixels = functionAddress52;
                if (functionAddress52 == 0L) {
                    b85 = false;
                    break Label_1278;
                }
            }
            b85 = true;
        }
        final boolean b86 = b84 & b85;
        final long functionAddress53 = GLContext.getFunctionAddress("glDrawElements");
        this.glDrawElements = functionAddress53;
        final boolean b87 = b86 & functionAddress53 != 0L;
        final long functionAddress54 = GLContext.getFunctionAddress("glDrawBuffer");
        this.glDrawBuffer = functionAddress54;
        final boolean b88 = b87 & functionAddress54 != 0L;
        final long functionAddress55 = GLContext.getFunctionAddress("glDrawArrays");
        this.glDrawArrays = functionAddress55;
        final boolean b89 = b88 & functionAddress55 != 0L;
        final long functionAddress56 = GLContext.getFunctionAddress("glDepthRange");
        this.glDepthRange = functionAddress56;
        final boolean b90 = b89 & functionAddress56 != 0L;
        final long functionAddress57 = GLContext.getFunctionAddress("glDepthMask");
        this.glDepthMask = functionAddress57;
        final boolean b91 = b90 & functionAddress57 != 0L;
        final long functionAddress58 = GLContext.getFunctionAddress("glDepthFunc");
        this.glDepthFunc = functionAddress58;
        final boolean b92 = b91 & functionAddress58 != 0L;
        boolean b93 = false;
        Label_1436: {
            if (!b) {
                final long functionAddress59 = GLContext.getFunctionAddress("glFeedbackBuffer");
                this.glFeedbackBuffer = functionAddress59;
                if (functionAddress59 == 0L) {
                    b93 = false;
                    break Label_1436;
                }
            }
            b93 = true;
        }
        final boolean b94 = b92 & b93;
        boolean b95 = false;
        Label_1462: {
            if (!b) {
                final long functionAddress60 = GLContext.getFunctionAddress("glGetPixelMapfv");
                this.glGetPixelMapfv = functionAddress60;
                if (functionAddress60 == 0L) {
                    b95 = false;
                    break Label_1462;
                }
            }
            b95 = true;
        }
        final boolean b96 = b94 & b95;
        boolean b97 = false;
        Label_1488: {
            if (!b) {
                final long functionAddress61 = GLContext.getFunctionAddress("glGetPixelMapuiv");
                this.glGetPixelMapuiv = functionAddress61;
                if (functionAddress61 == 0L) {
                    b97 = false;
                    break Label_1488;
                }
            }
            b97 = true;
        }
        final boolean b98 = b96 & b97;
        boolean b99 = false;
        Label_1514: {
            if (!b) {
                final long functionAddress62 = GLContext.getFunctionAddress("glGetPixelMapusv");
                this.glGetPixelMapusv = functionAddress62;
                if (functionAddress62 == 0L) {
                    b99 = false;
                    break Label_1514;
                }
            }
            b99 = true;
        }
        final boolean b100 = b98 & b99;
        boolean b101 = false;
        Label_1540: {
            if (!b) {
                final long functionAddress63 = GLContext.getFunctionAddress("glGetMaterialfv");
                this.glGetMaterialfv = functionAddress63;
                if (functionAddress63 == 0L) {
                    b101 = false;
                    break Label_1540;
                }
            }
            b101 = true;
        }
        final boolean b102 = b100 & b101;
        boolean b103 = false;
        Label_1566: {
            if (!b) {
                final long functionAddress64 = GLContext.getFunctionAddress("glGetMaterialiv");
                this.glGetMaterialiv = functionAddress64;
                if (functionAddress64 == 0L) {
                    b103 = false;
                    break Label_1566;
                }
            }
            b103 = true;
        }
        final boolean b104 = b102 & b103;
        boolean b105 = false;
        Label_1592: {
            if (!b) {
                final long functionAddress65 = GLContext.getFunctionAddress("glGetMapfv");
                this.glGetMapfv = functionAddress65;
                if (functionAddress65 == 0L) {
                    b105 = false;
                    break Label_1592;
                }
            }
            b105 = true;
        }
        final boolean b106 = b104 & b105;
        boolean b107 = false;
        Label_1618: {
            if (!b) {
                final long functionAddress66 = GLContext.getFunctionAddress("glGetMapdv");
                this.glGetMapdv = functionAddress66;
                if (functionAddress66 == 0L) {
                    b107 = false;
                    break Label_1618;
                }
            }
            b107 = true;
        }
        final boolean b108 = b106 & b107;
        boolean b109 = false;
        Label_1644: {
            if (!b) {
                final long functionAddress67 = GLContext.getFunctionAddress("glGetMapiv");
                this.glGetMapiv = functionAddress67;
                if (functionAddress67 == 0L) {
                    b109 = false;
                    break Label_1644;
                }
            }
            b109 = true;
        }
        final boolean b110 = b108 & b109;
        boolean b111 = false;
        Label_1670: {
            if (!b) {
                final long functionAddress68 = GLContext.getFunctionAddress("glGetLightfv");
                this.glGetLightfv = functionAddress68;
                if (functionAddress68 == 0L) {
                    b111 = false;
                    break Label_1670;
                }
            }
            b111 = true;
        }
        final boolean b112 = b110 & b111;
        boolean b113 = false;
        Label_1696: {
            if (!b) {
                final long functionAddress69 = GLContext.getFunctionAddress("glGetLightiv");
                this.glGetLightiv = functionAddress69;
                if (functionAddress69 == 0L) {
                    b113 = false;
                    break Label_1696;
                }
            }
            b113 = true;
        }
        final boolean b114 = b112 & b113;
        final long functionAddress70 = GLContext.getFunctionAddress("glGetError");
        this.glGetError = functionAddress70;
        final boolean b115 = b114 & functionAddress70 != 0L;
        final long functionAddress71 = GLContext.getFunctionAddress("glGetClipPlane");
        this.glGetClipPlane = functionAddress71;
        final boolean b116 = b115 & functionAddress71 != 0L;
        final long functionAddress72 = GLContext.getFunctionAddress("glGetBooleanv");
        this.glGetBooleanv = functionAddress72;
        final boolean b117 = b116 & functionAddress72 != 0L;
        final long functionAddress73 = GLContext.getFunctionAddress("glGetDoublev");
        this.glGetDoublev = functionAddress73;
        final boolean b118 = b117 & functionAddress73 != 0L;
        final long functionAddress74 = GLContext.getFunctionAddress("glGetFloatv");
        this.glGetFloatv = functionAddress74;
        final boolean b119 = b118 & functionAddress74 != 0L;
        final long functionAddress75 = GLContext.getFunctionAddress("glGetIntegerv");
        this.glGetIntegerv = functionAddress75;
        final boolean b120 = b119 & functionAddress75 != 0L;
        final long functionAddress76 = GLContext.getFunctionAddress("glGenTextures");
        this.glGenTextures = functionAddress76;
        final boolean b121 = b120 & functionAddress76 != 0L;
        boolean b122 = false;
        Label_1876: {
            if (!b) {
                final long functionAddress77 = GLContext.getFunctionAddress("glGenLists");
                this.glGenLists = functionAddress77;
                if (functionAddress77 == 0L) {
                    b122 = false;
                    break Label_1876;
                }
            }
            b122 = true;
        }
        final boolean b123 = b121 & b122;
        boolean b124 = false;
        Label_1902: {
            if (!b) {
                final long functionAddress78 = GLContext.getFunctionAddress("glFrustum");
                this.glFrustum = functionAddress78;
                if (functionAddress78 == 0L) {
                    b124 = false;
                    break Label_1902;
                }
            }
            b124 = true;
        }
        final boolean b125 = b123 & b124;
        final long functionAddress79 = GLContext.getFunctionAddress("glFrontFace");
        this.glFrontFace = functionAddress79;
        final boolean b126 = b125 & functionAddress79 != 0L;
        boolean b127 = false;
        Label_1950: {
            if (!b) {
                final long functionAddress80 = GLContext.getFunctionAddress("glFogf");
                this.glFogf = functionAddress80;
                if (functionAddress80 == 0L) {
                    b127 = false;
                    break Label_1950;
                }
            }
            b127 = true;
        }
        final boolean b128 = b126 & b127;
        boolean b129 = false;
        Label_1976: {
            if (!b) {
                final long functionAddress81 = GLContext.getFunctionAddress("glFogi");
                this.glFogi = functionAddress81;
                if (functionAddress81 == 0L) {
                    b129 = false;
                    break Label_1976;
                }
            }
            b129 = true;
        }
        final boolean b130 = b128 & b129;
        boolean b131 = false;
        Label_2002: {
            if (!b) {
                final long functionAddress82 = GLContext.getFunctionAddress("glFogfv");
                this.glFogfv = functionAddress82;
                if (functionAddress82 == 0L) {
                    b131 = false;
                    break Label_2002;
                }
            }
            b131 = true;
        }
        final boolean b132 = b130 & b131;
        boolean b133 = false;
        Label_2028: {
            if (!b) {
                final long functionAddress83 = GLContext.getFunctionAddress("glFogiv");
                this.glFogiv = functionAddress83;
                if (functionAddress83 == 0L) {
                    b133 = false;
                    break Label_2028;
                }
            }
            b133 = true;
        }
        final boolean b134 = b132 & b133;
        final long functionAddress84 = GLContext.getFunctionAddress("glFlush");
        this.glFlush = functionAddress84;
        final boolean b135 = b134 & functionAddress84 != 0L;
        final long functionAddress85 = GLContext.getFunctionAddress("glFinish");
        this.glFinish = functionAddress85;
        final boolean b136 = b135 & functionAddress85 != 0L;
        final long functionAddress86 = GLContext.getFunctionAddress("glGetPointerv");
        this.glGetPointerv = functionAddress86;
        final boolean b137 = b136 & functionAddress86 != 0L;
        final long functionAddress87 = GLContext.getFunctionAddress("glIsEnabled");
        this.glIsEnabled = functionAddress87;
        final boolean b138 = b137 & functionAddress87 != 0L;
        final long functionAddress88 = GLContext.getFunctionAddress("glInterleavedArrays");
        this.glInterleavedArrays = functionAddress88;
        final boolean b139 = b138 & functionAddress88 != 0L;
        boolean b140 = false;
        Label_2164: {
            if (!b) {
                final long functionAddress89 = GLContext.getFunctionAddress("glInitNames");
                this.glInitNames = functionAddress89;
                if (functionAddress89 == 0L) {
                    b140 = false;
                    break Label_2164;
                }
            }
            b140 = true;
        }
        final boolean b141 = b139 & b140;
        final long functionAddress90 = GLContext.getFunctionAddress("glHint");
        this.glHint = functionAddress90;
        final boolean b142 = b141 & functionAddress90 != 0L;
        final long functionAddress91 = GLContext.getFunctionAddress("glGetTexParameterfv");
        this.glGetTexParameterfv = functionAddress91;
        final boolean b143 = b142 & functionAddress91 != 0L;
        final long functionAddress92 = GLContext.getFunctionAddress("glGetTexParameteriv");
        this.glGetTexParameteriv = functionAddress92;
        final boolean b144 = b143 & functionAddress92 != 0L;
        final long functionAddress93 = GLContext.getFunctionAddress("glGetTexLevelParameterfv");
        this.glGetTexLevelParameterfv = functionAddress93;
        final boolean b145 = b144 & functionAddress93 != 0L;
        final long functionAddress94 = GLContext.getFunctionAddress("glGetTexLevelParameteriv");
        this.glGetTexLevelParameteriv = functionAddress94;
        final boolean b146 = b145 & functionAddress94 != 0L;
        final long functionAddress95 = GLContext.getFunctionAddress("glGetTexImage");
        this.glGetTexImage = functionAddress95;
        final boolean b147 = b146 & functionAddress95 != 0L;
        boolean b148 = false;
        Label_2322: {
            if (!b) {
                final long functionAddress96 = GLContext.getFunctionAddress("glGetTexGeniv");
                this.glGetTexGeniv = functionAddress96;
                if (functionAddress96 == 0L) {
                    b148 = false;
                    break Label_2322;
                }
            }
            b148 = true;
        }
        final boolean b149 = b147 & b148;
        boolean b150 = false;
        Label_2348: {
            if (!b) {
                final long functionAddress97 = GLContext.getFunctionAddress("glGetTexGenfv");
                this.glGetTexGenfv = functionAddress97;
                if (functionAddress97 == 0L) {
                    b150 = false;
                    break Label_2348;
                }
            }
            b150 = true;
        }
        final boolean b151 = b149 & b150;
        boolean b152 = false;
        Label_2374: {
            if (!b) {
                final long functionAddress98 = GLContext.getFunctionAddress("glGetTexGendv");
                this.glGetTexGendv = functionAddress98;
                if (functionAddress98 == 0L) {
                    b152 = false;
                    break Label_2374;
                }
            }
            b152 = true;
        }
        final boolean b153 = b151 & b152;
        final long functionAddress99 = GLContext.getFunctionAddress("glGetTexEnviv");
        this.glGetTexEnviv = functionAddress99;
        final boolean b154 = b153 & functionAddress99 != 0L;
        final long functionAddress100 = GLContext.getFunctionAddress("glGetTexEnvfv");
        this.glGetTexEnvfv = functionAddress100;
        final boolean b155 = b154 & functionAddress100 != 0L;
        final long functionAddress101 = GLContext.getFunctionAddress("glGetString");
        this.glGetString = functionAddress101;
        final boolean b156 = b155 & functionAddress101 != 0L;
        boolean b157 = false;
        Label_2466: {
            if (!b) {
                final long functionAddress102 = GLContext.getFunctionAddress("glGetPolygonStipple");
                this.glGetPolygonStipple = functionAddress102;
                if (functionAddress102 == 0L) {
                    b157 = false;
                    break Label_2466;
                }
            }
            b157 = true;
        }
        final boolean b158 = b156 & b157;
        boolean b159 = false;
        Label_2492: {
            if (!b) {
                final long functionAddress103 = GLContext.getFunctionAddress("glIsList");
                this.glIsList = functionAddress103;
                if (functionAddress103 == 0L) {
                    b159 = false;
                    break Label_2492;
                }
            }
            b159 = true;
        }
        final boolean b160 = b158 & b159;
        boolean b161 = false;
        Label_2518: {
            if (!b) {
                final long functionAddress104 = GLContext.getFunctionAddress("glMaterialf");
                this.glMaterialf = functionAddress104;
                if (functionAddress104 == 0L) {
                    b161 = false;
                    break Label_2518;
                }
            }
            b161 = true;
        }
        final boolean b162 = b160 & b161;
        boolean b163 = false;
        Label_2544: {
            if (!b) {
                final long functionAddress105 = GLContext.getFunctionAddress("glMateriali");
                this.glMateriali = functionAddress105;
                if (functionAddress105 == 0L) {
                    b163 = false;
                    break Label_2544;
                }
            }
            b163 = true;
        }
        final boolean b164 = b162 & b163;
        boolean b165 = false;
        Label_2570: {
            if (!b) {
                final long functionAddress106 = GLContext.getFunctionAddress("glMaterialfv");
                this.glMaterialfv = functionAddress106;
                if (functionAddress106 == 0L) {
                    b165 = false;
                    break Label_2570;
                }
            }
            b165 = true;
        }
        final boolean b166 = b164 & b165;
        boolean b167 = false;
        Label_2596: {
            if (!b) {
                final long functionAddress107 = GLContext.getFunctionAddress("glMaterialiv");
                this.glMaterialiv = functionAddress107;
                if (functionAddress107 == 0L) {
                    b167 = false;
                    break Label_2596;
                }
            }
            b167 = true;
        }
        final boolean b168 = b166 & b167;
        boolean b169 = false;
        Label_2622: {
            if (!b) {
                final long functionAddress108 = GLContext.getFunctionAddress("glMapGrid1f");
                this.glMapGrid1f = functionAddress108;
                if (functionAddress108 == 0L) {
                    b169 = false;
                    break Label_2622;
                }
            }
            b169 = true;
        }
        final boolean b170 = b168 & b169;
        boolean b171 = false;
        Label_2648: {
            if (!b) {
                final long functionAddress109 = GLContext.getFunctionAddress("glMapGrid1d");
                this.glMapGrid1d = functionAddress109;
                if (functionAddress109 == 0L) {
                    b171 = false;
                    break Label_2648;
                }
            }
            b171 = true;
        }
        final boolean b172 = b170 & b171;
        boolean b173 = false;
        Label_2674: {
            if (!b) {
                final long functionAddress110 = GLContext.getFunctionAddress("glMapGrid2f");
                this.glMapGrid2f = functionAddress110;
                if (functionAddress110 == 0L) {
                    b173 = false;
                    break Label_2674;
                }
            }
            b173 = true;
        }
        final boolean b174 = b172 & b173;
        boolean b175 = false;
        Label_2700: {
            if (!b) {
                final long functionAddress111 = GLContext.getFunctionAddress("glMapGrid2d");
                this.glMapGrid2d = functionAddress111;
                if (functionAddress111 == 0L) {
                    b175 = false;
                    break Label_2700;
                }
            }
            b175 = true;
        }
        final boolean b176 = b174 & b175;
        boolean b177 = false;
        Label_2726: {
            if (!b) {
                final long functionAddress112 = GLContext.getFunctionAddress("glMap2f");
                this.glMap2f = functionAddress112;
                if (functionAddress112 == 0L) {
                    b177 = false;
                    break Label_2726;
                }
            }
            b177 = true;
        }
        final boolean b178 = b176 & b177;
        boolean b179 = false;
        Label_2752: {
            if (!b) {
                final long functionAddress113 = GLContext.getFunctionAddress("glMap2d");
                this.glMap2d = functionAddress113;
                if (functionAddress113 == 0L) {
                    b179 = false;
                    break Label_2752;
                }
            }
            b179 = true;
        }
        final boolean b180 = b178 & b179;
        boolean b181 = false;
        Label_2778: {
            if (!b) {
                final long functionAddress114 = GLContext.getFunctionAddress("glMap1f");
                this.glMap1f = functionAddress114;
                if (functionAddress114 == 0L) {
                    b181 = false;
                    break Label_2778;
                }
            }
            b181 = true;
        }
        final boolean b182 = b180 & b181;
        boolean b183 = false;
        Label_2804: {
            if (!b) {
                final long functionAddress115 = GLContext.getFunctionAddress("glMap1d");
                this.glMap1d = functionAddress115;
                if (functionAddress115 == 0L) {
                    b183 = false;
                    break Label_2804;
                }
            }
            b183 = true;
        }
        final boolean b184 = b182 & b183;
        final long functionAddress116 = GLContext.getFunctionAddress("glLogicOp");
        this.glLogicOp = functionAddress116;
        final boolean b185 = b184 & functionAddress116 != 0L;
        boolean b186 = false;
        Label_2852: {
            if (!b) {
                final long functionAddress117 = GLContext.getFunctionAddress("glLoadName");
                this.glLoadName = functionAddress117;
                if (functionAddress117 == 0L) {
                    b186 = false;
                    break Label_2852;
                }
            }
            b186 = true;
        }
        final boolean b187 = b185 & b186;
        boolean b188 = false;
        Label_2878: {
            if (!b) {
                final long functionAddress118 = GLContext.getFunctionAddress("glLoadMatrixf");
                this.glLoadMatrixf = functionAddress118;
                if (functionAddress118 == 0L) {
                    b188 = false;
                    break Label_2878;
                }
            }
            b188 = true;
        }
        final boolean b189 = b187 & b188;
        boolean b190 = false;
        Label_2904: {
            if (!b) {
                final long functionAddress119 = GLContext.getFunctionAddress("glLoadMatrixd");
                this.glLoadMatrixd = functionAddress119;
                if (functionAddress119 == 0L) {
                    b190 = false;
                    break Label_2904;
                }
            }
            b190 = true;
        }
        final boolean b191 = b189 & b190;
        boolean b192 = false;
        Label_2930: {
            if (!b) {
                final long functionAddress120 = GLContext.getFunctionAddress("glLoadIdentity");
                this.glLoadIdentity = functionAddress120;
                if (functionAddress120 == 0L) {
                    b192 = false;
                    break Label_2930;
                }
            }
            b192 = true;
        }
        final boolean b193 = b191 & b192;
        boolean b194 = false;
        Label_2956: {
            if (!b) {
                final long functionAddress121 = GLContext.getFunctionAddress("glListBase");
                this.glListBase = functionAddress121;
                if (functionAddress121 == 0L) {
                    b194 = false;
                    break Label_2956;
                }
            }
            b194 = true;
        }
        final boolean b195 = b193 & b194;
        final long functionAddress122 = GLContext.getFunctionAddress("glLineWidth");
        this.glLineWidth = functionAddress122;
        final boolean b196 = b195 & functionAddress122 != 0L;
        boolean b197 = false;
        Label_3004: {
            if (!b) {
                final long functionAddress123 = GLContext.getFunctionAddress("glLineStipple");
                this.glLineStipple = functionAddress123;
                if (functionAddress123 == 0L) {
                    b197 = false;
                    break Label_3004;
                }
            }
            b197 = true;
        }
        final boolean b198 = b196 & b197;
        boolean b199 = false;
        Label_3030: {
            if (!b) {
                final long functionAddress124 = GLContext.getFunctionAddress("glLightModelf");
                this.glLightModelf = functionAddress124;
                if (functionAddress124 == 0L) {
                    b199 = false;
                    break Label_3030;
                }
            }
            b199 = true;
        }
        final boolean b200 = b198 & b199;
        boolean b201 = false;
        Label_3056: {
            if (!b) {
                final long functionAddress125 = GLContext.getFunctionAddress("glLightModeli");
                this.glLightModeli = functionAddress125;
                if (functionAddress125 == 0L) {
                    b201 = false;
                    break Label_3056;
                }
            }
            b201 = true;
        }
        final boolean b202 = b200 & b201;
        boolean b203 = false;
        Label_3082: {
            if (!b) {
                final long functionAddress126 = GLContext.getFunctionAddress("glLightModelfv");
                this.glLightModelfv = functionAddress126;
                if (functionAddress126 == 0L) {
                    b203 = false;
                    break Label_3082;
                }
            }
            b203 = true;
        }
        final boolean b204 = b202 & b203;
        boolean b205 = false;
        Label_3108: {
            if (!b) {
                final long functionAddress127 = GLContext.getFunctionAddress("glLightModeliv");
                this.glLightModeliv = functionAddress127;
                if (functionAddress127 == 0L) {
                    b205 = false;
                    break Label_3108;
                }
            }
            b205 = true;
        }
        final boolean b206 = b204 & b205;
        boolean b207 = false;
        Label_3134: {
            if (!b) {
                final long functionAddress128 = GLContext.getFunctionAddress("glLightf");
                this.glLightf = functionAddress128;
                if (functionAddress128 == 0L) {
                    b207 = false;
                    break Label_3134;
                }
            }
            b207 = true;
        }
        final boolean b208 = b206 & b207;
        boolean b209 = false;
        Label_3160: {
            if (!b) {
                final long functionAddress129 = GLContext.getFunctionAddress("glLighti");
                this.glLighti = functionAddress129;
                if (functionAddress129 == 0L) {
                    b209 = false;
                    break Label_3160;
                }
            }
            b209 = true;
        }
        final boolean b210 = b208 & b209;
        boolean b211 = false;
        Label_3186: {
            if (!b) {
                final long functionAddress130 = GLContext.getFunctionAddress("glLightfv");
                this.glLightfv = functionAddress130;
                if (functionAddress130 == 0L) {
                    b211 = false;
                    break Label_3186;
                }
            }
            b211 = true;
        }
        final boolean b212 = b210 & b211;
        boolean b213 = false;
        Label_3212: {
            if (!b) {
                final long functionAddress131 = GLContext.getFunctionAddress("glLightiv");
                this.glLightiv = functionAddress131;
                if (functionAddress131 == 0L) {
                    b213 = false;
                    break Label_3212;
                }
            }
            b213 = true;
        }
        final boolean b214 = b212 & b213;
        final long functionAddress132 = GLContext.getFunctionAddress("glIsTexture");
        this.glIsTexture = functionAddress132;
        final boolean b215 = b214 & functionAddress132 != 0L;
        boolean b216 = false;
        Label_3260: {
            if (!b) {
                final long functionAddress133 = GLContext.getFunctionAddress("glMatrixMode");
                this.glMatrixMode = functionAddress133;
                if (functionAddress133 == 0L) {
                    b216 = false;
                    break Label_3260;
                }
            }
            b216 = true;
        }
        final boolean b217 = b215 & b216;
        boolean b218 = false;
        Label_3286: {
            if (!b) {
                final long functionAddress134 = GLContext.getFunctionAddress("glPolygonStipple");
                this.glPolygonStipple = functionAddress134;
                if (functionAddress134 == 0L) {
                    b218 = false;
                    break Label_3286;
                }
            }
            b218 = true;
        }
        final boolean b219 = b217 & b218;
        final long functionAddress135 = GLContext.getFunctionAddress("glPolygonOffset");
        this.glPolygonOffset = functionAddress135;
        final boolean b220 = b219 & functionAddress135 != 0L;
        final long functionAddress136 = GLContext.getFunctionAddress("glPolygonMode");
        this.glPolygonMode = functionAddress136;
        final boolean b221 = b220 & functionAddress136 != 0L;
        final long functionAddress137 = GLContext.getFunctionAddress("glPointSize");
        this.glPointSize = functionAddress137;
        final boolean b222 = b221 & functionAddress137 != 0L;
        boolean b223 = false;
        Label_3378: {
            if (!b) {
                final long functionAddress138 = GLContext.getFunctionAddress("glPixelZoom");
                this.glPixelZoom = functionAddress138;
                if (functionAddress138 == 0L) {
                    b223 = false;
                    break Label_3378;
                }
            }
            b223 = true;
        }
        final boolean b224 = b222 & b223;
        boolean b225 = false;
        Label_3404: {
            if (!b) {
                final long functionAddress139 = GLContext.getFunctionAddress("glPixelTransferf");
                this.glPixelTransferf = functionAddress139;
                if (functionAddress139 == 0L) {
                    b225 = false;
                    break Label_3404;
                }
            }
            b225 = true;
        }
        final boolean b226 = b224 & b225;
        boolean b227 = false;
        Label_3430: {
            if (!b) {
                final long functionAddress140 = GLContext.getFunctionAddress("glPixelTransferi");
                this.glPixelTransferi = functionAddress140;
                if (functionAddress140 == 0L) {
                    b227 = false;
                    break Label_3430;
                }
            }
            b227 = true;
        }
        final boolean b228 = b226 & b227;
        final long functionAddress141 = GLContext.getFunctionAddress("glPixelStoref");
        this.glPixelStoref = functionAddress141;
        final boolean b229 = b228 & functionAddress141 != 0L;
        final long functionAddress142 = GLContext.getFunctionAddress("glPixelStorei");
        this.glPixelStorei = functionAddress142;
        final boolean b230 = b229 & functionAddress142 != 0L;
        boolean b231 = false;
        Label_3500: {
            if (!b) {
                final long functionAddress143 = GLContext.getFunctionAddress("glPixelMapfv");
                this.glPixelMapfv = functionAddress143;
                if (functionAddress143 == 0L) {
                    b231 = false;
                    break Label_3500;
                }
            }
            b231 = true;
        }
        final boolean b232 = b230 & b231;
        boolean b233 = false;
        Label_3526: {
            if (!b) {
                final long functionAddress144 = GLContext.getFunctionAddress("glPixelMapuiv");
                this.glPixelMapuiv = functionAddress144;
                if (functionAddress144 == 0L) {
                    b233 = false;
                    break Label_3526;
                }
            }
            b233 = true;
        }
        final boolean b234 = b232 & b233;
        boolean b235 = false;
        Label_3552: {
            if (!b) {
                final long functionAddress145 = GLContext.getFunctionAddress("glPixelMapusv");
                this.glPixelMapusv = functionAddress145;
                if (functionAddress145 == 0L) {
                    b235 = false;
                    break Label_3552;
                }
            }
            b235 = true;
        }
        final boolean b236 = b234 & b235;
        boolean b237 = false;
        Label_3578: {
            if (!b) {
                final long functionAddress146 = GLContext.getFunctionAddress("glPassThrough");
                this.glPassThrough = functionAddress146;
                if (functionAddress146 == 0L) {
                    b237 = false;
                    break Label_3578;
                }
            }
            b237 = true;
        }
        final boolean b238 = b236 & b237;
        boolean b239 = false;
        Label_3604: {
            if (!b) {
                final long functionAddress147 = GLContext.getFunctionAddress("glOrtho");
                this.glOrtho = functionAddress147;
                if (functionAddress147 == 0L) {
                    b239 = false;
                    break Label_3604;
                }
            }
            b239 = true;
        }
        final boolean b240 = b238 & b239;
        boolean b241 = false;
        Label_3630: {
            if (!b) {
                final long functionAddress148 = GLContext.getFunctionAddress("glNormalPointer");
                this.glNormalPointer = functionAddress148;
                if (functionAddress148 == 0L) {
                    b241 = false;
                    break Label_3630;
                }
            }
            b241 = true;
        }
        final boolean b242 = b240 & b241;
        boolean b243 = false;
        Label_3656: {
            if (!b) {
                final long functionAddress149 = GLContext.getFunctionAddress("glNormal3b");
                this.glNormal3b = functionAddress149;
                if (functionAddress149 == 0L) {
                    b243 = false;
                    break Label_3656;
                }
            }
            b243 = true;
        }
        final boolean b244 = b242 & b243;
        boolean b245 = false;
        Label_3682: {
            if (!b) {
                final long functionAddress150 = GLContext.getFunctionAddress("glNormal3f");
                this.glNormal3f = functionAddress150;
                if (functionAddress150 == 0L) {
                    b245 = false;
                    break Label_3682;
                }
            }
            b245 = true;
        }
        final boolean b246 = b244 & b245;
        boolean b247 = false;
        Label_3708: {
            if (!b) {
                final long functionAddress151 = GLContext.getFunctionAddress("glNormal3d");
                this.glNormal3d = functionAddress151;
                if (functionAddress151 == 0L) {
                    b247 = false;
                    break Label_3708;
                }
            }
            b247 = true;
        }
        final boolean b248 = b246 & b247;
        boolean b249 = false;
        Label_3734: {
            if (!b) {
                final long functionAddress152 = GLContext.getFunctionAddress("glNormal3i");
                this.glNormal3i = functionAddress152;
                if (functionAddress152 == 0L) {
                    b249 = false;
                    break Label_3734;
                }
            }
            b249 = true;
        }
        final boolean b250 = b248 & b249;
        boolean b251 = false;
        Label_3760: {
            if (!b) {
                final long functionAddress153 = GLContext.getFunctionAddress("glNewList");
                this.glNewList = functionAddress153;
                if (functionAddress153 == 0L) {
                    b251 = false;
                    break Label_3760;
                }
            }
            b251 = true;
        }
        final boolean b252 = b250 & b251;
        boolean b253 = false;
        Label_3786: {
            if (!b) {
                final long functionAddress154 = GLContext.getFunctionAddress("glEndList");
                this.glEndList = functionAddress154;
                if (functionAddress154 == 0L) {
                    b253 = false;
                    break Label_3786;
                }
            }
            b253 = true;
        }
        final boolean b254 = b252 & b253;
        boolean b255 = false;
        Label_3812: {
            if (!b) {
                final long functionAddress155 = GLContext.getFunctionAddress("glMultMatrixf");
                this.glMultMatrixf = functionAddress155;
                if (functionAddress155 == 0L) {
                    b255 = false;
                    break Label_3812;
                }
            }
            b255 = true;
        }
        final boolean b256 = b254 & b255;
        boolean b257 = false;
        Label_3838: {
            if (!b) {
                final long functionAddress156 = GLContext.getFunctionAddress("glMultMatrixd");
                this.glMultMatrixd = functionAddress156;
                if (functionAddress156 == 0L) {
                    b257 = false;
                    break Label_3838;
                }
            }
            b257 = true;
        }
        final boolean b258 = b256 & b257;
        final long functionAddress157 = GLContext.getFunctionAddress("glShadeModel");
        this.glShadeModel = functionAddress157;
        final boolean b259 = b258 & functionAddress157 != 0L;
        boolean b260 = false;
        Label_3886: {
            if (!b) {
                final long functionAddress158 = GLContext.getFunctionAddress("glSelectBuffer");
                this.glSelectBuffer = functionAddress158;
                if (functionAddress158 == 0L) {
                    b260 = false;
                    break Label_3886;
                }
            }
            b260 = true;
        }
        final boolean b261 = b259 & b260;
        final long functionAddress159 = GLContext.getFunctionAddress("glScissor");
        this.glScissor = functionAddress159;
        final boolean b262 = b261 & functionAddress159 != 0L;
        boolean b263 = false;
        Label_3934: {
            if (!b) {
                final long functionAddress160 = GLContext.getFunctionAddress("glScalef");
                this.glScalef = functionAddress160;
                if (functionAddress160 == 0L) {
                    b263 = false;
                    break Label_3934;
                }
            }
            b263 = true;
        }
        final boolean b264 = b262 & b263;
        boolean b265 = false;
        Label_3960: {
            if (!b) {
                final long functionAddress161 = GLContext.getFunctionAddress("glScaled");
                this.glScaled = functionAddress161;
                if (functionAddress161 == 0L) {
                    b265 = false;
                    break Label_3960;
                }
            }
            b265 = true;
        }
        final boolean b266 = b264 & b265;
        boolean b267 = false;
        Label_3986: {
            if (!b) {
                final long functionAddress162 = GLContext.getFunctionAddress("glRotatef");
                this.glRotatef = functionAddress162;
                if (functionAddress162 == 0L) {
                    b267 = false;
                    break Label_3986;
                }
            }
            b267 = true;
        }
        final boolean b268 = b266 & b267;
        boolean b269 = false;
        Label_4012: {
            if (!b) {
                final long functionAddress163 = GLContext.getFunctionAddress("glRotated");
                this.glRotated = functionAddress163;
                if (functionAddress163 == 0L) {
                    b269 = false;
                    break Label_4012;
                }
            }
            b269 = true;
        }
        final boolean b270 = b268 & b269;
        boolean b271 = false;
        Label_4038: {
            if (!b) {
                final long functionAddress164 = GLContext.getFunctionAddress("glRenderMode");
                this.glRenderMode = functionAddress164;
                if (functionAddress164 == 0L) {
                    b271 = false;
                    break Label_4038;
                }
            }
            b271 = true;
        }
        final boolean b272 = b270 & b271;
        boolean b273 = false;
        Label_4064: {
            if (!b) {
                final long functionAddress165 = GLContext.getFunctionAddress("glRectf");
                this.glRectf = functionAddress165;
                if (functionAddress165 == 0L) {
                    b273 = false;
                    break Label_4064;
                }
            }
            b273 = true;
        }
        final boolean b274 = b272 & b273;
        boolean b275 = false;
        Label_4090: {
            if (!b) {
                final long functionAddress166 = GLContext.getFunctionAddress("glRectd");
                this.glRectd = functionAddress166;
                if (functionAddress166 == 0L) {
                    b275 = false;
                    break Label_4090;
                }
            }
            b275 = true;
        }
        final boolean b276 = b274 & b275;
        boolean b277 = false;
        Label_4116: {
            if (!b) {
                final long functionAddress167 = GLContext.getFunctionAddress("glRecti");
                this.glRecti = functionAddress167;
                if (functionAddress167 == 0L) {
                    b277 = false;
                    break Label_4116;
                }
            }
            b277 = true;
        }
        final boolean b278 = b276 & b277;
        final long functionAddress168 = GLContext.getFunctionAddress("glReadPixels");
        this.glReadPixels = functionAddress168;
        final boolean b279 = b278 & functionAddress168 != 0L;
        final long functionAddress169 = GLContext.getFunctionAddress("glReadBuffer");
        this.glReadBuffer = functionAddress169;
        final boolean b280 = b279 & functionAddress169 != 0L;
        boolean b281 = false;
        Label_4186: {
            if (!b) {
                final long functionAddress170 = GLContext.getFunctionAddress("glRasterPos2f");
                this.glRasterPos2f = functionAddress170;
                if (functionAddress170 == 0L) {
                    b281 = false;
                    break Label_4186;
                }
            }
            b281 = true;
        }
        final boolean b282 = b280 & b281;
        boolean b283 = false;
        Label_4212: {
            if (!b) {
                final long functionAddress171 = GLContext.getFunctionAddress("glRasterPos2d");
                this.glRasterPos2d = functionAddress171;
                if (functionAddress171 == 0L) {
                    b283 = false;
                    break Label_4212;
                }
            }
            b283 = true;
        }
        final boolean b284 = b282 & b283;
        boolean b285 = false;
        Label_4238: {
            if (!b) {
                final long functionAddress172 = GLContext.getFunctionAddress("glRasterPos2i");
                this.glRasterPos2i = functionAddress172;
                if (functionAddress172 == 0L) {
                    b285 = false;
                    break Label_4238;
                }
            }
            b285 = true;
        }
        final boolean b286 = b284 & b285;
        boolean b287 = false;
        Label_4264: {
            if (!b) {
                final long functionAddress173 = GLContext.getFunctionAddress("glRasterPos3f");
                this.glRasterPos3f = functionAddress173;
                if (functionAddress173 == 0L) {
                    b287 = false;
                    break Label_4264;
                }
            }
            b287 = true;
        }
        final boolean b288 = b286 & b287;
        boolean b289 = false;
        Label_4290: {
            if (!b) {
                final long functionAddress174 = GLContext.getFunctionAddress("glRasterPos3d");
                this.glRasterPos3d = functionAddress174;
                if (functionAddress174 == 0L) {
                    b289 = false;
                    break Label_4290;
                }
            }
            b289 = true;
        }
        final boolean b290 = b288 & b289;
        boolean b291 = false;
        Label_4316: {
            if (!b) {
                final long functionAddress175 = GLContext.getFunctionAddress("glRasterPos3i");
                this.glRasterPos3i = functionAddress175;
                if (functionAddress175 == 0L) {
                    b291 = false;
                    break Label_4316;
                }
            }
            b291 = true;
        }
        final boolean b292 = b290 & b291;
        boolean b293 = false;
        Label_4342: {
            if (!b) {
                final long functionAddress176 = GLContext.getFunctionAddress("glRasterPos4f");
                this.glRasterPos4f = functionAddress176;
                if (functionAddress176 == 0L) {
                    b293 = false;
                    break Label_4342;
                }
            }
            b293 = true;
        }
        final boolean b294 = b292 & b293;
        boolean b295 = false;
        Label_4368: {
            if (!b) {
                final long functionAddress177 = GLContext.getFunctionAddress("glRasterPos4d");
                this.glRasterPos4d = functionAddress177;
                if (functionAddress177 == 0L) {
                    b295 = false;
                    break Label_4368;
                }
            }
            b295 = true;
        }
        final boolean b296 = b294 & b295;
        boolean b297 = false;
        Label_4394: {
            if (!b) {
                final long functionAddress178 = GLContext.getFunctionAddress("glRasterPos4i");
                this.glRasterPos4i = functionAddress178;
                if (functionAddress178 == 0L) {
                    b297 = false;
                    break Label_4394;
                }
            }
            b297 = true;
        }
        final boolean b298 = b296 & b297;
        boolean b299 = false;
        Label_4420: {
            if (!b) {
                final long functionAddress179 = GLContext.getFunctionAddress("glPushName");
                this.glPushName = functionAddress179;
                if (functionAddress179 == 0L) {
                    b299 = false;
                    break Label_4420;
                }
            }
            b299 = true;
        }
        final boolean b300 = b298 & b299;
        boolean b301 = false;
        Label_4446: {
            if (!b) {
                final long functionAddress180 = GLContext.getFunctionAddress("glPopName");
                this.glPopName = functionAddress180;
                if (functionAddress180 == 0L) {
                    b301 = false;
                    break Label_4446;
                }
            }
            b301 = true;
        }
        final boolean b302 = b300 & b301;
        boolean b303 = false;
        Label_4472: {
            if (!b) {
                final long functionAddress181 = GLContext.getFunctionAddress("glPushMatrix");
                this.glPushMatrix = functionAddress181;
                if (functionAddress181 == 0L) {
                    b303 = false;
                    break Label_4472;
                }
            }
            b303 = true;
        }
        final boolean b304 = b302 & b303;
        boolean b305 = false;
        Label_4498: {
            if (!b) {
                final long functionAddress182 = GLContext.getFunctionAddress("glPopMatrix");
                this.glPopMatrix = functionAddress182;
                if (functionAddress182 == 0L) {
                    b305 = false;
                    break Label_4498;
                }
            }
            b305 = true;
        }
        final boolean b306 = b304 & b305;
        boolean b307 = false;
        Label_4524: {
            if (!b) {
                final long functionAddress183 = GLContext.getFunctionAddress("glPushClientAttrib");
                this.glPushClientAttrib = functionAddress183;
                if (functionAddress183 == 0L) {
                    b307 = false;
                    break Label_4524;
                }
            }
            b307 = true;
        }
        final boolean b308 = b306 & b307;
        boolean b309 = false;
        Label_4550: {
            if (!b) {
                final long functionAddress184 = GLContext.getFunctionAddress("glPopClientAttrib");
                this.glPopClientAttrib = functionAddress184;
                if (functionAddress184 == 0L) {
                    b309 = false;
                    break Label_4550;
                }
            }
            b309 = true;
        }
        final boolean b310 = b308 & b309;
        boolean b311 = false;
        Label_4576: {
            if (!b) {
                final long functionAddress185 = GLContext.getFunctionAddress("glPushAttrib");
                this.glPushAttrib = functionAddress185;
                if (functionAddress185 == 0L) {
                    b311 = false;
                    break Label_4576;
                }
            }
            b311 = true;
        }
        final boolean b312 = b310 & b311;
        boolean b313 = false;
        Label_4602: {
            if (!b) {
                final long functionAddress186 = GLContext.getFunctionAddress("glPopAttrib");
                this.glPopAttrib = functionAddress186;
                if (functionAddress186 == 0L) {
                    b313 = false;
                    break Label_4602;
                }
            }
            b313 = true;
        }
        final boolean b314 = b312 & b313;
        final long functionAddress187 = GLContext.getFunctionAddress("glStencilFunc");
        this.glStencilFunc = functionAddress187;
        final boolean b315 = b314 & functionAddress187 != 0L;
        boolean b316 = false;
        Label_4650: {
            if (!b) {
                final long functionAddress188 = GLContext.getFunctionAddress("glVertexPointer");
                this.glVertexPointer = functionAddress188;
                if (functionAddress188 == 0L) {
                    b316 = false;
                    break Label_4650;
                }
            }
            b316 = true;
        }
        final boolean b317 = b315 & b316;
        boolean b318 = false;
        Label_4676: {
            if (!b) {
                final long functionAddress189 = GLContext.getFunctionAddress("glVertex2f");
                this.glVertex2f = functionAddress189;
                if (functionAddress189 == 0L) {
                    b318 = false;
                    break Label_4676;
                }
            }
            b318 = true;
        }
        final boolean b319 = b317 & b318;
        boolean b320 = false;
        Label_4702: {
            if (!b) {
                final long functionAddress190 = GLContext.getFunctionAddress("glVertex2d");
                this.glVertex2d = functionAddress190;
                if (functionAddress190 == 0L) {
                    b320 = false;
                    break Label_4702;
                }
            }
            b320 = true;
        }
        final boolean b321 = b319 & b320;
        boolean b322 = false;
        Label_4728: {
            if (!b) {
                final long functionAddress191 = GLContext.getFunctionAddress("glVertex2i");
                this.glVertex2i = functionAddress191;
                if (functionAddress191 == 0L) {
                    b322 = false;
                    break Label_4728;
                }
            }
            b322 = true;
        }
        final boolean b323 = b321 & b322;
        boolean b324 = false;
        Label_4754: {
            if (!b) {
                final long functionAddress192 = GLContext.getFunctionAddress("glVertex3f");
                this.glVertex3f = functionAddress192;
                if (functionAddress192 == 0L) {
                    b324 = false;
                    break Label_4754;
                }
            }
            b324 = true;
        }
        final boolean b325 = b323 & b324;
        boolean b326 = false;
        Label_4780: {
            if (!b) {
                final long functionAddress193 = GLContext.getFunctionAddress("glVertex3d");
                this.glVertex3d = functionAddress193;
                if (functionAddress193 == 0L) {
                    b326 = false;
                    break Label_4780;
                }
            }
            b326 = true;
        }
        final boolean b327 = b325 & b326;
        boolean b328 = false;
        Label_4806: {
            if (!b) {
                final long functionAddress194 = GLContext.getFunctionAddress("glVertex3i");
                this.glVertex3i = functionAddress194;
                if (functionAddress194 == 0L) {
                    b328 = false;
                    break Label_4806;
                }
            }
            b328 = true;
        }
        final boolean b329 = b327 & b328;
        boolean b330 = false;
        Label_4832: {
            if (!b) {
                final long functionAddress195 = GLContext.getFunctionAddress("glVertex4f");
                this.glVertex4f = functionAddress195;
                if (functionAddress195 == 0L) {
                    b330 = false;
                    break Label_4832;
                }
            }
            b330 = true;
        }
        final boolean b331 = b329 & b330;
        boolean b332 = false;
        Label_4858: {
            if (!b) {
                final long functionAddress196 = GLContext.getFunctionAddress("glVertex4d");
                this.glVertex4d = functionAddress196;
                if (functionAddress196 == 0L) {
                    b332 = false;
                    break Label_4858;
                }
            }
            b332 = true;
        }
        final boolean b333 = b331 & b332;
        boolean b334 = false;
        Label_4884: {
            if (!b) {
                final long functionAddress197 = GLContext.getFunctionAddress("glVertex4i");
                this.glVertex4i = functionAddress197;
                if (functionAddress197 == 0L) {
                    b334 = false;
                    break Label_4884;
                }
            }
            b334 = true;
        }
        final boolean b335 = b333 & b334;
        boolean b336 = false;
        Label_4910: {
            if (!b) {
                final long functionAddress198 = GLContext.getFunctionAddress("glTranslatef");
                this.glTranslatef = functionAddress198;
                if (functionAddress198 == 0L) {
                    b336 = false;
                    break Label_4910;
                }
            }
            b336 = true;
        }
        final boolean b337 = b335 & b336;
        boolean b338 = false;
        Label_4936: {
            if (!b) {
                final long functionAddress199 = GLContext.getFunctionAddress("glTranslated");
                this.glTranslated = functionAddress199;
                if (functionAddress199 == 0L) {
                    b338 = false;
                    break Label_4936;
                }
            }
            b338 = true;
        }
        final boolean b339 = b337 & b338;
        final long functionAddress200 = GLContext.getFunctionAddress("glTexImage1D");
        this.glTexImage1D = functionAddress200;
        final boolean b340 = b339 & functionAddress200 != 0L;
        final long functionAddress201 = GLContext.getFunctionAddress("glTexImage2D");
        this.glTexImage2D = functionAddress201;
        final boolean b341 = b340 & functionAddress201 != 0L;
        final long functionAddress202 = GLContext.getFunctionAddress("glTexSubImage1D");
        this.glTexSubImage1D = functionAddress202;
        final boolean b342 = b341 & functionAddress202 != 0L;
        final long functionAddress203 = GLContext.getFunctionAddress("glTexSubImage2D");
        this.glTexSubImage2D = functionAddress203;
        final boolean b343 = b342 & functionAddress203 != 0L;
        final long functionAddress204 = GLContext.getFunctionAddress("glTexParameterf");
        this.glTexParameterf = functionAddress204;
        final boolean b344 = b343 & functionAddress204 != 0L;
        final long functionAddress205 = GLContext.getFunctionAddress("glTexParameteri");
        this.glTexParameteri = functionAddress205;
        final boolean b345 = b344 & functionAddress205 != 0L;
        final long functionAddress206 = GLContext.getFunctionAddress("glTexParameterfv");
        this.glTexParameterfv = functionAddress206;
        final boolean b346 = b345 & functionAddress206 != 0L;
        final long functionAddress207 = GLContext.getFunctionAddress("glTexParameteriv");
        this.glTexParameteriv = functionAddress207;
        final boolean b347 = b346 & functionAddress207 != 0L;
        boolean b348 = false;
        Label_5138: {
            if (!b) {
                final long functionAddress208 = GLContext.getFunctionAddress("glTexGenf");
                this.glTexGenf = functionAddress208;
                if (functionAddress208 == 0L) {
                    b348 = false;
                    break Label_5138;
                }
            }
            b348 = true;
        }
        final boolean b349 = b347 & b348;
        boolean b350 = false;
        Label_5164: {
            if (!b) {
                final long functionAddress209 = GLContext.getFunctionAddress("glTexGend");
                this.glTexGend = functionAddress209;
                if (functionAddress209 == 0L) {
                    b350 = false;
                    break Label_5164;
                }
            }
            b350 = true;
        }
        final boolean b351 = b349 & b350;
        boolean b352 = false;
        Label_5190: {
            if (!b) {
                final long functionAddress210 = GLContext.getFunctionAddress("glTexGenfv");
                this.glTexGenfv = functionAddress210;
                if (functionAddress210 == 0L) {
                    b352 = false;
                    break Label_5190;
                }
            }
            b352 = true;
        }
        final boolean b353 = b351 & b352;
        boolean b354 = false;
        Label_5216: {
            if (!b) {
                final long functionAddress211 = GLContext.getFunctionAddress("glTexGendv");
                this.glTexGendv = functionAddress211;
                if (functionAddress211 == 0L) {
                    b354 = false;
                    break Label_5216;
                }
            }
            b354 = true;
        }
        final boolean b355 = b353 & b354;
        boolean b356 = false;
        Label_5242: {
            if (!b) {
                final long functionAddress212 = GLContext.getFunctionAddress("glTexGeni");
                this.glTexGeni = functionAddress212;
                if (functionAddress212 == 0L) {
                    b356 = false;
                    break Label_5242;
                }
            }
            b356 = true;
        }
        final boolean b357 = b355 & b356;
        boolean b358 = false;
        Label_5268: {
            if (!b) {
                final long functionAddress213 = GLContext.getFunctionAddress("glTexGeniv");
                this.glTexGeniv = functionAddress213;
                if (functionAddress213 == 0L) {
                    b358 = false;
                    break Label_5268;
                }
            }
            b358 = true;
        }
        final boolean b359 = b357 & b358;
        final long functionAddress214 = GLContext.getFunctionAddress("glTexEnvf");
        this.glTexEnvf = functionAddress214;
        final boolean b360 = b359 & functionAddress214 != 0L;
        final long functionAddress215 = GLContext.getFunctionAddress("glTexEnvi");
        this.glTexEnvi = functionAddress215;
        final boolean b361 = b360 & functionAddress215 != 0L;
        final long functionAddress216 = GLContext.getFunctionAddress("glTexEnvfv");
        this.glTexEnvfv = functionAddress216;
        final boolean b362 = b361 & functionAddress216 != 0L;
        final long functionAddress217 = GLContext.getFunctionAddress("glTexEnviv");
        this.glTexEnviv = functionAddress217;
        final boolean b363 = b362 & functionAddress217 != 0L;
        boolean b364 = false;
        Label_5382: {
            if (!b) {
                final long functionAddress218 = GLContext.getFunctionAddress("glTexCoordPointer");
                this.glTexCoordPointer = functionAddress218;
                if (functionAddress218 == 0L) {
                    b364 = false;
                    break Label_5382;
                }
            }
            b364 = true;
        }
        final boolean b365 = b363 & b364;
        boolean b366 = false;
        Label_5408: {
            if (!b) {
                final long functionAddress219 = GLContext.getFunctionAddress("glTexCoord1f");
                this.glTexCoord1f = functionAddress219;
                if (functionAddress219 == 0L) {
                    b366 = false;
                    break Label_5408;
                }
            }
            b366 = true;
        }
        final boolean b367 = b365 & b366;
        boolean b368 = false;
        Label_5434: {
            if (!b) {
                final long functionAddress220 = GLContext.getFunctionAddress("glTexCoord1d");
                this.glTexCoord1d = functionAddress220;
                if (functionAddress220 == 0L) {
                    b368 = false;
                    break Label_5434;
                }
            }
            b368 = true;
        }
        final boolean b369 = b367 & b368;
        boolean b370 = false;
        Label_5460: {
            if (!b) {
                final long functionAddress221 = GLContext.getFunctionAddress("glTexCoord2f");
                this.glTexCoord2f = functionAddress221;
                if (functionAddress221 == 0L) {
                    b370 = false;
                    break Label_5460;
                }
            }
            b370 = true;
        }
        final boolean b371 = b369 & b370;
        boolean b372 = false;
        Label_5486: {
            if (!b) {
                final long functionAddress222 = GLContext.getFunctionAddress("glTexCoord2d");
                this.glTexCoord2d = functionAddress222;
                if (functionAddress222 == 0L) {
                    b372 = false;
                    break Label_5486;
                }
            }
            b372 = true;
        }
        final boolean b373 = b371 & b372;
        boolean b374 = false;
        Label_5512: {
            if (!b) {
                final long functionAddress223 = GLContext.getFunctionAddress("glTexCoord3f");
                this.glTexCoord3f = functionAddress223;
                if (functionAddress223 == 0L) {
                    b374 = false;
                    break Label_5512;
                }
            }
            b374 = true;
        }
        final boolean b375 = b373 & b374;
        boolean b376 = false;
        Label_5538: {
            if (!b) {
                final long functionAddress224 = GLContext.getFunctionAddress("glTexCoord3d");
                this.glTexCoord3d = functionAddress224;
                if (functionAddress224 == 0L) {
                    b376 = false;
                    break Label_5538;
                }
            }
            b376 = true;
        }
        final boolean b377 = b375 & b376;
        boolean b378 = false;
        Label_5564: {
            if (!b) {
                final long functionAddress225 = GLContext.getFunctionAddress("glTexCoord4f");
                this.glTexCoord4f = functionAddress225;
                if (functionAddress225 == 0L) {
                    b378 = false;
                    break Label_5564;
                }
            }
            b378 = true;
        }
        final boolean b379 = b377 & b378;
        boolean b380 = false;
        Label_5590: {
            if (!b) {
                final long functionAddress226 = GLContext.getFunctionAddress("glTexCoord4d");
                this.glTexCoord4d = functionAddress226;
                if (functionAddress226 == 0L) {
                    b380 = false;
                    break Label_5590;
                }
            }
            b380 = true;
        }
        final boolean b381 = b379 & b380;
        final long functionAddress227 = GLContext.getFunctionAddress("glStencilOp");
        this.glStencilOp = functionAddress227;
        final boolean b382 = b381 & functionAddress227 != 0L;
        final long functionAddress228 = GLContext.getFunctionAddress("glStencilMask");
        this.glStencilMask = functionAddress228;
        final boolean b383 = b382 & functionAddress228 != 0L;
        final long functionAddress229 = GLContext.getFunctionAddress("glViewport");
        this.glViewport = functionAddress229;
        return b383 & functionAddress229 != 0L;
    }
    
    private boolean GL12_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawRangeElements");
        this.glDrawRangeElements = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glTexImage3D");
        this.glTexImage3D = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glTexSubImage3D");
        this.glTexSubImage3D = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glCopyTexSubImage3D");
        this.glCopyTexSubImage3D = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean GL13_initNativeFunctionAddresses(final boolean b) {
        final long functionAddress = GLContext.getFunctionAddress("glActiveTexture");
        this.glActiveTexture = functionAddress;
        final boolean b2 = functionAddress != 0L;
        boolean b3 = false;
        Label_0046: {
            if (!b) {
                final long functionAddress2 = GLContext.getFunctionAddress("glClientActiveTexture");
                this.glClientActiveTexture = functionAddress2;
                if (functionAddress2 == 0L) {
                    b3 = false;
                    break Label_0046;
                }
            }
            b3 = true;
        }
        final boolean b4 = b2 & b3;
        final long functionAddress3 = GLContext.getFunctionAddress("glCompressedTexImage1D");
        this.glCompressedTexImage1D = functionAddress3;
        final boolean b5 = b4 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glCompressedTexImage2D");
        this.glCompressedTexImage2D = functionAddress4;
        final boolean b6 = b5 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glCompressedTexImage3D");
        this.glCompressedTexImage3D = functionAddress5;
        final boolean b7 = b6 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glCompressedTexSubImage1D");
        this.glCompressedTexSubImage1D = functionAddress6;
        final boolean b8 = b7 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glCompressedTexSubImage2D");
        this.glCompressedTexSubImage2D = functionAddress7;
        final boolean b9 = b8 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glCompressedTexSubImage3D");
        this.glCompressedTexSubImage3D = functionAddress8;
        final boolean b10 = b9 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetCompressedTexImage");
        this.glGetCompressedTexImage = functionAddress9;
        final boolean b11 = b10 & functionAddress9 != 0L;
        boolean b12 = false;
        Label_0226: {
            if (!b) {
                final long functionAddress10 = GLContext.getFunctionAddress("glMultiTexCoord1f");
                this.glMultiTexCoord1f = functionAddress10;
                if (functionAddress10 == 0L) {
                    b12 = false;
                    break Label_0226;
                }
            }
            b12 = true;
        }
        final boolean b13 = b11 & b12;
        boolean b14 = false;
        Label_0252: {
            if (!b) {
                final long functionAddress11 = GLContext.getFunctionAddress("glMultiTexCoord1d");
                this.glMultiTexCoord1d = functionAddress11;
                if (functionAddress11 == 0L) {
                    b14 = false;
                    break Label_0252;
                }
            }
            b14 = true;
        }
        final boolean b15 = b13 & b14;
        boolean b16 = false;
        Label_0278: {
            if (!b) {
                final long functionAddress12 = GLContext.getFunctionAddress("glMultiTexCoord2f");
                this.glMultiTexCoord2f = functionAddress12;
                if (functionAddress12 == 0L) {
                    b16 = false;
                    break Label_0278;
                }
            }
            b16 = true;
        }
        final boolean b17 = b15 & b16;
        boolean b18 = false;
        Label_0304: {
            if (!b) {
                final long functionAddress13 = GLContext.getFunctionAddress("glMultiTexCoord2d");
                this.glMultiTexCoord2d = functionAddress13;
                if (functionAddress13 == 0L) {
                    b18 = false;
                    break Label_0304;
                }
            }
            b18 = true;
        }
        final boolean b19 = b17 & b18;
        boolean b20 = false;
        Label_0330: {
            if (!b) {
                final long functionAddress14 = GLContext.getFunctionAddress("glMultiTexCoord3f");
                this.glMultiTexCoord3f = functionAddress14;
                if (functionAddress14 == 0L) {
                    b20 = false;
                    break Label_0330;
                }
            }
            b20 = true;
        }
        final boolean b21 = b19 & b20;
        boolean b22 = false;
        Label_0356: {
            if (!b) {
                final long functionAddress15 = GLContext.getFunctionAddress("glMultiTexCoord3d");
                this.glMultiTexCoord3d = functionAddress15;
                if (functionAddress15 == 0L) {
                    b22 = false;
                    break Label_0356;
                }
            }
            b22 = true;
        }
        final boolean b23 = b21 & b22;
        boolean b24 = false;
        Label_0382: {
            if (!b) {
                final long functionAddress16 = GLContext.getFunctionAddress("glMultiTexCoord4f");
                this.glMultiTexCoord4f = functionAddress16;
                if (functionAddress16 == 0L) {
                    b24 = false;
                    break Label_0382;
                }
            }
            b24 = true;
        }
        final boolean b25 = b23 & b24;
        boolean b26 = false;
        Label_0408: {
            if (!b) {
                final long functionAddress17 = GLContext.getFunctionAddress("glMultiTexCoord4d");
                this.glMultiTexCoord4d = functionAddress17;
                if (functionAddress17 == 0L) {
                    b26 = false;
                    break Label_0408;
                }
            }
            b26 = true;
        }
        final boolean b27 = b25 & b26;
        boolean b28 = false;
        Label_0434: {
            if (!b) {
                final long functionAddress18 = GLContext.getFunctionAddress("glLoadTransposeMatrixf");
                this.glLoadTransposeMatrixf = functionAddress18;
                if (functionAddress18 == 0L) {
                    b28 = false;
                    break Label_0434;
                }
            }
            b28 = true;
        }
        final boolean b29 = b27 & b28;
        boolean b30 = false;
        Label_0460: {
            if (!b) {
                final long functionAddress19 = GLContext.getFunctionAddress("glLoadTransposeMatrixd");
                this.glLoadTransposeMatrixd = functionAddress19;
                if (functionAddress19 == 0L) {
                    b30 = false;
                    break Label_0460;
                }
            }
            b30 = true;
        }
        final boolean b31 = b29 & b30;
        boolean b32 = false;
        Label_0486: {
            if (!b) {
                final long functionAddress20 = GLContext.getFunctionAddress("glMultTransposeMatrixf");
                this.glMultTransposeMatrixf = functionAddress20;
                if (functionAddress20 == 0L) {
                    b32 = false;
                    break Label_0486;
                }
            }
            b32 = true;
        }
        final boolean b33 = b31 & b32;
        boolean b34 = false;
        Label_0512: {
            if (!b) {
                final long functionAddress21 = GLContext.getFunctionAddress("glMultTransposeMatrixd");
                this.glMultTransposeMatrixd = functionAddress21;
                if (functionAddress21 == 0L) {
                    b34 = false;
                    break Label_0512;
                }
            }
            b34 = true;
        }
        final boolean b35 = b33 & b34;
        final long functionAddress22 = GLContext.getFunctionAddress("glSampleCoverage");
        this.glSampleCoverage = functionAddress22;
        return b35 & functionAddress22 != 0L;
    }
    
    private boolean GL14_initNativeFunctionAddresses(final boolean b) {
        final long functionAddress = GLContext.getFunctionAddress("glBlendEquation");
        this.glBlendEquation = functionAddress;
        final boolean b2 = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBlendColor");
        this.glBlendColor = functionAddress2;
        final boolean b3 = b2 & functionAddress2 != 0L;
        boolean b4 = false;
        Label_0068: {
            if (!b) {
                final long functionAddress3 = GLContext.getFunctionAddress("glFogCoordf");
                this.glFogCoordf = functionAddress3;
                if (functionAddress3 == 0L) {
                    b4 = false;
                    break Label_0068;
                }
            }
            b4 = true;
        }
        final boolean b5 = b3 & b4;
        boolean b6 = false;
        Label_0094: {
            if (!b) {
                final long functionAddress4 = GLContext.getFunctionAddress("glFogCoordd");
                this.glFogCoordd = functionAddress4;
                if (functionAddress4 == 0L) {
                    b6 = false;
                    break Label_0094;
                }
            }
            b6 = true;
        }
        final boolean b7 = b5 & b6;
        boolean b8 = false;
        Label_0120: {
            if (!b) {
                final long functionAddress5 = GLContext.getFunctionAddress("glFogCoordPointer");
                this.glFogCoordPointer = functionAddress5;
                if (functionAddress5 == 0L) {
                    b8 = false;
                    break Label_0120;
                }
            }
            b8 = true;
        }
        final boolean b9 = b7 & b8;
        final long functionAddress6 = GLContext.getFunctionAddress("glMultiDrawArrays");
        this.glMultiDrawArrays = functionAddress6;
        final boolean b10 = b9 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glPointParameteri");
        this.glPointParameteri = functionAddress7;
        final boolean b11 = b10 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glPointParameterf");
        this.glPointParameterf = functionAddress8;
        final boolean b12 = b11 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glPointParameteriv");
        this.glPointParameteriv = functionAddress9;
        final boolean b13 = b12 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glPointParameterfv");
        this.glPointParameterfv = functionAddress10;
        final boolean b14 = b13 & functionAddress10 != 0L;
        boolean b15 = false;
        Label_0256: {
            if (!b) {
                final long functionAddress11 = GLContext.getFunctionAddress("glSecondaryColor3b");
                this.glSecondaryColor3b = functionAddress11;
                if (functionAddress11 == 0L) {
                    b15 = false;
                    break Label_0256;
                }
            }
            b15 = true;
        }
        final boolean b16 = b14 & b15;
        boolean b17 = false;
        Label_0282: {
            if (!b) {
                final long functionAddress12 = GLContext.getFunctionAddress("glSecondaryColor3f");
                this.glSecondaryColor3f = functionAddress12;
                if (functionAddress12 == 0L) {
                    b17 = false;
                    break Label_0282;
                }
            }
            b17 = true;
        }
        final boolean b18 = b16 & b17;
        boolean b19 = false;
        Label_0308: {
            if (!b) {
                final long functionAddress13 = GLContext.getFunctionAddress("glSecondaryColor3d");
                this.glSecondaryColor3d = functionAddress13;
                if (functionAddress13 == 0L) {
                    b19 = false;
                    break Label_0308;
                }
            }
            b19 = true;
        }
        final boolean b20 = b18 & b19;
        boolean b21 = false;
        Label_0334: {
            if (!b) {
                final long functionAddress14 = GLContext.getFunctionAddress("glSecondaryColor3ub");
                this.glSecondaryColor3ub = functionAddress14;
                if (functionAddress14 == 0L) {
                    b21 = false;
                    break Label_0334;
                }
            }
            b21 = true;
        }
        final boolean b22 = b20 & b21;
        boolean b23 = false;
        Label_0360: {
            if (!b) {
                final long functionAddress15 = GLContext.getFunctionAddress("glSecondaryColorPointer");
                this.glSecondaryColorPointer = functionAddress15;
                if (functionAddress15 == 0L) {
                    b23 = false;
                    break Label_0360;
                }
            }
            b23 = true;
        }
        final boolean b24 = b22 & b23;
        final long functionAddress16 = GLContext.getFunctionAddress("glBlendFuncSeparate");
        this.glBlendFuncSeparate = functionAddress16;
        final boolean b25 = b24 & functionAddress16 != 0L;
        boolean b26 = false;
        Label_0408: {
            if (!b) {
                final long functionAddress17 = GLContext.getFunctionAddress("glWindowPos2f");
                this.glWindowPos2f = functionAddress17;
                if (functionAddress17 == 0L) {
                    b26 = false;
                    break Label_0408;
                }
            }
            b26 = true;
        }
        final boolean b27 = b25 & b26;
        boolean b28 = false;
        Label_0434: {
            if (!b) {
                final long functionAddress18 = GLContext.getFunctionAddress("glWindowPos2d");
                this.glWindowPos2d = functionAddress18;
                if (functionAddress18 == 0L) {
                    b28 = false;
                    break Label_0434;
                }
            }
            b28 = true;
        }
        final boolean b29 = b27 & b28;
        boolean b30 = false;
        Label_0460: {
            if (!b) {
                final long functionAddress19 = GLContext.getFunctionAddress("glWindowPos2i");
                this.glWindowPos2i = functionAddress19;
                if (functionAddress19 == 0L) {
                    b30 = false;
                    break Label_0460;
                }
            }
            b30 = true;
        }
        final boolean b31 = b29 & b30;
        boolean b32 = false;
        Label_0486: {
            if (!b) {
                final long functionAddress20 = GLContext.getFunctionAddress("glWindowPos3f");
                this.glWindowPos3f = functionAddress20;
                if (functionAddress20 == 0L) {
                    b32 = false;
                    break Label_0486;
                }
            }
            b32 = true;
        }
        final boolean b33 = b31 & b32;
        boolean b34 = false;
        Label_0512: {
            if (!b) {
                final long functionAddress21 = GLContext.getFunctionAddress("glWindowPos3d");
                this.glWindowPos3d = functionAddress21;
                if (functionAddress21 == 0L) {
                    b34 = false;
                    break Label_0512;
                }
            }
            b34 = true;
        }
        final boolean b35 = b33 & b34;
        if (!b) {
            final long functionAddress22 = GLContext.getFunctionAddress("glWindowPos3i");
            this.glWindowPos3i = functionAddress22;
            if (functionAddress22 == 0L) {
                final boolean b36 = false;
                return b35 & b36;
            }
        }
        final boolean b36 = true;
        return b35 & b36;
    }
    
    private boolean GL15_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindBuffer");
        this.glBindBuffer = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteBuffers");
        this.glDeleteBuffers = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGenBuffers");
        this.glGenBuffers = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glIsBuffer");
        this.glIsBuffer = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glBufferData");
        this.glBufferData = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glBufferSubData");
        this.glBufferSubData = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetBufferSubData");
        this.glGetBufferSubData = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glMapBuffer");
        this.glMapBuffer = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glUnmapBuffer");
        this.glUnmapBuffer = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glGetBufferParameteriv");
        this.glGetBufferParameteriv = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGetBufferPointerv");
        this.glGetBufferPointerv = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glGenQueries");
        this.glGenQueries = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glDeleteQueries");
        this.glDeleteQueries = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glIsQuery");
        this.glIsQuery = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glBeginQuery");
        this.glBeginQuery = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glEndQuery");
        this.glEndQuery = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glGetQueryiv");
        this.glGetQueryiv = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetQueryObjectiv");
        this.glGetQueryObjectiv = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glGetQueryObjectuiv");
        this.glGetQueryObjectuiv = functionAddress19;
        return b18 & functionAddress19 != 0L;
    }
    
    private boolean GL20_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glShaderSource");
        this.glShaderSource = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glCreateShader");
        this.glCreateShader = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glIsShader");
        this.glIsShader = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glCompileShader");
        this.glCompileShader = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glDeleteShader");
        this.glDeleteShader = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glCreateProgram");
        this.glCreateProgram = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glIsProgram");
        this.glIsProgram = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glAttachShader");
        this.glAttachShader = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glDetachShader");
        this.glDetachShader = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glLinkProgram");
        this.glLinkProgram = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glUseProgram");
        this.glUseProgram = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glValidateProgram");
        this.glValidateProgram = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glDeleteProgram");
        this.glDeleteProgram = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glUniform1f");
        this.glUniform1f = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glUniform2f");
        this.glUniform2f = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glUniform3f");
        this.glUniform3f = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glUniform4f");
        this.glUniform4f = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glUniform1i");
        this.glUniform1i = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glUniform2i");
        this.glUniform2i = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glUniform3i");
        this.glUniform3i = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glUniform4i");
        this.glUniform4i = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glUniform1fv");
        this.glUniform1fv = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glUniform2fv");
        this.glUniform2fv = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glUniform3fv");
        this.glUniform3fv = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glUniform4fv");
        this.glUniform4fv = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glUniform1iv");
        this.glUniform1iv = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glUniform2iv");
        this.glUniform2iv = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glUniform3iv");
        this.glUniform3iv = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glUniform4iv");
        this.glUniform4iv = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glUniformMatrix2fv");
        this.glUniformMatrix2fv = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glUniformMatrix3fv");
        this.glUniformMatrix3fv = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glUniformMatrix4fv");
        this.glUniformMatrix4fv = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glGetShaderiv");
        this.glGetShaderiv = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glGetProgramiv");
        this.glGetProgramiv = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glGetShaderInfoLog");
        this.glGetShaderInfoLog = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glGetProgramInfoLog");
        this.glGetProgramInfoLog = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glGetAttachedShaders");
        this.glGetAttachedShaders = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glGetUniformLocation");
        this.glGetUniformLocation = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glGetActiveUniform");
        this.glGetActiveUniform = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glGetUniformfv");
        this.glGetUniformfv = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glGetUniformiv");
        this.glGetUniformiv = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glGetShaderSource");
        this.glGetShaderSource = functionAddress42;
        final boolean b42 = b41 & functionAddress42 != 0L;
        final long functionAddress43 = GLContext.getFunctionAddress("glVertexAttrib1s");
        this.glVertexAttrib1s = functionAddress43;
        final boolean b43 = b42 & functionAddress43 != 0L;
        final long functionAddress44 = GLContext.getFunctionAddress("glVertexAttrib1f");
        this.glVertexAttrib1f = functionAddress44;
        final boolean b44 = b43 & functionAddress44 != 0L;
        final long functionAddress45 = GLContext.getFunctionAddress("glVertexAttrib1d");
        this.glVertexAttrib1d = functionAddress45;
        final boolean b45 = b44 & functionAddress45 != 0L;
        final long functionAddress46 = GLContext.getFunctionAddress("glVertexAttrib2s");
        this.glVertexAttrib2s = functionAddress46;
        final boolean b46 = b45 & functionAddress46 != 0L;
        final long functionAddress47 = GLContext.getFunctionAddress("glVertexAttrib2f");
        this.glVertexAttrib2f = functionAddress47;
        final boolean b47 = b46 & functionAddress47 != 0L;
        final long functionAddress48 = GLContext.getFunctionAddress("glVertexAttrib2d");
        this.glVertexAttrib2d = functionAddress48;
        final boolean b48 = b47 & functionAddress48 != 0L;
        final long functionAddress49 = GLContext.getFunctionAddress("glVertexAttrib3s");
        this.glVertexAttrib3s = functionAddress49;
        final boolean b49 = b48 & functionAddress49 != 0L;
        final long functionAddress50 = GLContext.getFunctionAddress("glVertexAttrib3f");
        this.glVertexAttrib3f = functionAddress50;
        final boolean b50 = b49 & functionAddress50 != 0L;
        final long functionAddress51 = GLContext.getFunctionAddress("glVertexAttrib3d");
        this.glVertexAttrib3d = functionAddress51;
        final boolean b51 = b50 & functionAddress51 != 0L;
        final long functionAddress52 = GLContext.getFunctionAddress("glVertexAttrib4s");
        this.glVertexAttrib4s = functionAddress52;
        final boolean b52 = b51 & functionAddress52 != 0L;
        final long functionAddress53 = GLContext.getFunctionAddress("glVertexAttrib4f");
        this.glVertexAttrib4f = functionAddress53;
        final boolean b53 = b52 & functionAddress53 != 0L;
        final long functionAddress54 = GLContext.getFunctionAddress("glVertexAttrib4d");
        this.glVertexAttrib4d = functionAddress54;
        final boolean b54 = b53 & functionAddress54 != 0L;
        final long functionAddress55 = GLContext.getFunctionAddress("glVertexAttrib4Nub");
        this.glVertexAttrib4Nub = functionAddress55;
        final boolean b55 = b54 & functionAddress55 != 0L;
        final long functionAddress56 = GLContext.getFunctionAddress("glVertexAttribPointer");
        this.glVertexAttribPointer = functionAddress56;
        final boolean b56 = b55 & functionAddress56 != 0L;
        final long functionAddress57 = GLContext.getFunctionAddress("glEnableVertexAttribArray");
        this.glEnableVertexAttribArray = functionAddress57;
        final boolean b57 = b56 & functionAddress57 != 0L;
        final long functionAddress58 = GLContext.getFunctionAddress("glDisableVertexAttribArray");
        this.glDisableVertexAttribArray = functionAddress58;
        final boolean b58 = b57 & functionAddress58 != 0L;
        final long functionAddress59 = GLContext.getFunctionAddress("glGetVertexAttribfv");
        this.glGetVertexAttribfv = functionAddress59;
        final boolean b59 = b58 & functionAddress59 != 0L;
        final long functionAddress60 = GLContext.getFunctionAddress("glGetVertexAttribdv");
        this.glGetVertexAttribdv = functionAddress60;
        final boolean b60 = b59 & functionAddress60 != 0L;
        final long functionAddress61 = GLContext.getFunctionAddress("glGetVertexAttribiv");
        this.glGetVertexAttribiv = functionAddress61;
        final boolean b61 = b60 & functionAddress61 != 0L;
        final long functionAddress62 = GLContext.getFunctionAddress("glGetVertexAttribPointerv");
        this.glGetVertexAttribPointerv = functionAddress62;
        final boolean b62 = b61 & functionAddress62 != 0L;
        final long functionAddress63 = GLContext.getFunctionAddress("glBindAttribLocation");
        this.glBindAttribLocation = functionAddress63;
        final boolean b63 = b62 & functionAddress63 != 0L;
        final long functionAddress64 = GLContext.getFunctionAddress("glGetActiveAttrib");
        this.glGetActiveAttrib = functionAddress64;
        final boolean b64 = b63 & functionAddress64 != 0L;
        final long functionAddress65 = GLContext.getFunctionAddress("glGetAttribLocation");
        this.glGetAttribLocation = functionAddress65;
        final boolean b65 = b64 & functionAddress65 != 0L;
        final long functionAddress66 = GLContext.getFunctionAddress("glDrawBuffers");
        this.glDrawBuffers = functionAddress66;
        final boolean b66 = b65 & functionAddress66 != 0L;
        final long functionAddress67 = GLContext.getFunctionAddress("glStencilOpSeparate");
        this.glStencilOpSeparate = functionAddress67;
        final boolean b67 = b66 & functionAddress67 != 0L;
        final long functionAddress68 = GLContext.getFunctionAddress("glStencilFuncSeparate");
        this.glStencilFuncSeparate = functionAddress68;
        final boolean b68 = b67 & functionAddress68 != 0L;
        final long functionAddress69 = GLContext.getFunctionAddress("glStencilMaskSeparate");
        this.glStencilMaskSeparate = functionAddress69;
        final boolean b69 = b68 & functionAddress69 != 0L;
        final long functionAddress70 = GLContext.getFunctionAddress("glBlendEquationSeparate");
        this.glBlendEquationSeparate = functionAddress70;
        return b69 & functionAddress70 != 0L;
    }
    
    private boolean GL21_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glUniformMatrix2x3fv");
        this.glUniformMatrix2x3fv = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glUniformMatrix3x2fv");
        this.glUniformMatrix3x2fv = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glUniformMatrix2x4fv");
        this.glUniformMatrix2x4fv = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glUniformMatrix4x2fv");
        this.glUniformMatrix4x2fv = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glUniformMatrix3x4fv");
        this.glUniformMatrix3x4fv = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glUniformMatrix4x3fv");
        this.glUniformMatrix4x3fv = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean GL30_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetStringi");
        this.glGetStringi = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glClearBufferfv");
        this.glClearBufferfv = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glClearBufferiv");
        this.glClearBufferiv = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glClearBufferuiv");
        this.glClearBufferuiv = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glClearBufferfi");
        this.glClearBufferfi = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexAttribI1i");
        this.glVertexAttribI1i = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glVertexAttribI2i");
        this.glVertexAttribI2i = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glVertexAttribI3i");
        this.glVertexAttribI3i = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glVertexAttribI4i");
        this.glVertexAttribI4i = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVertexAttribI1ui");
        this.glVertexAttribI1ui = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glVertexAttribI2ui");
        this.glVertexAttribI2ui = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glVertexAttribI3ui");
        this.glVertexAttribI3ui = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glVertexAttribI4ui");
        this.glVertexAttribI4ui = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glVertexAttribI1iv");
        this.glVertexAttribI1iv = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glVertexAttribI2iv");
        this.glVertexAttribI2iv = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glVertexAttribI3iv");
        this.glVertexAttribI3iv = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glVertexAttribI4iv");
        this.glVertexAttribI4iv = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glVertexAttribI1uiv");
        this.glVertexAttribI1uiv = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glVertexAttribI2uiv");
        this.glVertexAttribI2uiv = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glVertexAttribI3uiv");
        this.glVertexAttribI3uiv = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glVertexAttribI4uiv");
        this.glVertexAttribI4uiv = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glVertexAttribI4bv");
        this.glVertexAttribI4bv = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glVertexAttribI4sv");
        this.glVertexAttribI4sv = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glVertexAttribI4ubv");
        this.glVertexAttribI4ubv = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glVertexAttribI4usv");
        this.glVertexAttribI4usv = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glVertexAttribIPointer");
        this.glVertexAttribIPointer = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glGetVertexAttribIiv");
        this.glGetVertexAttribIiv = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glGetVertexAttribIuiv");
        this.glGetVertexAttribIuiv = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glUniform1ui");
        this.glUniform1ui = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glUniform2ui");
        this.glUniform2ui = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glUniform3ui");
        this.glUniform3ui = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glUniform4ui");
        this.glUniform4ui = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glUniform1uiv");
        this.glUniform1uiv = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glUniform2uiv");
        this.glUniform2uiv = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glUniform3uiv");
        this.glUniform3uiv = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glUniform4uiv");
        this.glUniform4uiv = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glGetUniformuiv");
        this.glGetUniformuiv = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glBindFragDataLocation");
        this.glBindFragDataLocation = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glGetFragDataLocation");
        this.glGetFragDataLocation = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glBeginConditionalRender");
        this.glBeginConditionalRender = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glEndConditionalRender");
        this.glEndConditionalRender = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glMapBufferRange");
        this.glMapBufferRange = functionAddress42;
        final boolean b42 = b41 & functionAddress42 != 0L;
        final long functionAddress43 = GLContext.getFunctionAddress("glFlushMappedBufferRange");
        this.glFlushMappedBufferRange = functionAddress43;
        final boolean b43 = b42 & functionAddress43 != 0L;
        final long functionAddress44 = GLContext.getFunctionAddress("glClampColor");
        this.glClampColor = functionAddress44;
        final boolean b44 = b43 & functionAddress44 != 0L;
        final long functionAddress45 = GLContext.getFunctionAddress("glIsRenderbuffer");
        this.glIsRenderbuffer = functionAddress45;
        final boolean b45 = b44 & functionAddress45 != 0L;
        final long functionAddress46 = GLContext.getFunctionAddress("glBindRenderbuffer");
        this.glBindRenderbuffer = functionAddress46;
        final boolean b46 = b45 & functionAddress46 != 0L;
        final long functionAddress47 = GLContext.getFunctionAddress("glDeleteRenderbuffers");
        this.glDeleteRenderbuffers = functionAddress47;
        final boolean b47 = b46 & functionAddress47 != 0L;
        final long functionAddress48 = GLContext.getFunctionAddress("glGenRenderbuffers");
        this.glGenRenderbuffers = functionAddress48;
        final boolean b48 = b47 & functionAddress48 != 0L;
        final long functionAddress49 = GLContext.getFunctionAddress("glRenderbufferStorage");
        this.glRenderbufferStorage = functionAddress49;
        final boolean b49 = b48 & functionAddress49 != 0L;
        final long functionAddress50 = GLContext.getFunctionAddress("glGetRenderbufferParameteriv");
        this.glGetRenderbufferParameteriv = functionAddress50;
        final boolean b50 = b49 & functionAddress50 != 0L;
        final long functionAddress51 = GLContext.getFunctionAddress("glIsFramebuffer");
        this.glIsFramebuffer = functionAddress51;
        final boolean b51 = b50 & functionAddress51 != 0L;
        final long functionAddress52 = GLContext.getFunctionAddress("glBindFramebuffer");
        this.glBindFramebuffer = functionAddress52;
        final boolean b52 = b51 & functionAddress52 != 0L;
        final long functionAddress53 = GLContext.getFunctionAddress("glDeleteFramebuffers");
        this.glDeleteFramebuffers = functionAddress53;
        final boolean b53 = b52 & functionAddress53 != 0L;
        final long functionAddress54 = GLContext.getFunctionAddress("glGenFramebuffers");
        this.glGenFramebuffers = functionAddress54;
        final boolean b54 = b53 & functionAddress54 != 0L;
        final long functionAddress55 = GLContext.getFunctionAddress("glCheckFramebufferStatus");
        this.glCheckFramebufferStatus = functionAddress55;
        final boolean b55 = b54 & functionAddress55 != 0L;
        final long functionAddress56 = GLContext.getFunctionAddress("glFramebufferTexture1D");
        this.glFramebufferTexture1D = functionAddress56;
        final boolean b56 = b55 & functionAddress56 != 0L;
        final long functionAddress57 = GLContext.getFunctionAddress("glFramebufferTexture2D");
        this.glFramebufferTexture2D = functionAddress57;
        final boolean b57 = b56 & functionAddress57 != 0L;
        final long functionAddress58 = GLContext.getFunctionAddress("glFramebufferTexture3D");
        this.glFramebufferTexture3D = functionAddress58;
        final boolean b58 = b57 & functionAddress58 != 0L;
        final long functionAddress59 = GLContext.getFunctionAddress("glFramebufferRenderbuffer");
        this.glFramebufferRenderbuffer = functionAddress59;
        final boolean b59 = b58 & functionAddress59 != 0L;
        final long functionAddress60 = GLContext.getFunctionAddress("glGetFramebufferAttachmentParameteriv");
        this.glGetFramebufferAttachmentParameteriv = functionAddress60;
        final boolean b60 = b59 & functionAddress60 != 0L;
        final long functionAddress61 = GLContext.getFunctionAddress("glGenerateMipmap");
        this.glGenerateMipmap = functionAddress61;
        final boolean b61 = b60 & functionAddress61 != 0L;
        final long functionAddress62 = GLContext.getFunctionAddress("glRenderbufferStorageMultisample");
        this.glRenderbufferStorageMultisample = functionAddress62;
        final boolean b62 = b61 & functionAddress62 != 0L;
        final long functionAddress63 = GLContext.getFunctionAddress("glBlitFramebuffer");
        this.glBlitFramebuffer = functionAddress63;
        final boolean b63 = b62 & functionAddress63 != 0L;
        final long functionAddress64 = GLContext.getFunctionAddress("glTexParameterIiv");
        this.glTexParameterIiv = functionAddress64;
        final boolean b64 = b63 & functionAddress64 != 0L;
        final long functionAddress65 = GLContext.getFunctionAddress("glTexParameterIuiv");
        this.glTexParameterIuiv = functionAddress65;
        final boolean b65 = b64 & functionAddress65 != 0L;
        final long functionAddress66 = GLContext.getFunctionAddress("glGetTexParameterIiv");
        this.glGetTexParameterIiv = functionAddress66;
        final boolean b66 = b65 & functionAddress66 != 0L;
        final long functionAddress67 = GLContext.getFunctionAddress("glGetTexParameterIuiv");
        this.glGetTexParameterIuiv = functionAddress67;
        final boolean b67 = b66 & functionAddress67 != 0L;
        final long functionAddress68 = GLContext.getFunctionAddress("glFramebufferTextureLayer");
        this.glFramebufferTextureLayer = functionAddress68;
        final boolean b68 = b67 & functionAddress68 != 0L;
        final long functionAddress69 = GLContext.getFunctionAddress("glColorMaski");
        this.glColorMaski = functionAddress69;
        final boolean b69 = b68 & functionAddress69 != 0L;
        final long functionAddress70 = GLContext.getFunctionAddress("glGetBooleani_v");
        this.glGetBooleani_v = functionAddress70;
        final boolean b70 = b69 & functionAddress70 != 0L;
        final long functionAddress71 = GLContext.getFunctionAddress("glGetIntegeri_v");
        this.glGetIntegeri_v = functionAddress71;
        final boolean b71 = b70 & functionAddress71 != 0L;
        final long functionAddress72 = GLContext.getFunctionAddress("glEnablei");
        this.glEnablei = functionAddress72;
        final boolean b72 = b71 & functionAddress72 != 0L;
        final long functionAddress73 = GLContext.getFunctionAddress("glDisablei");
        this.glDisablei = functionAddress73;
        final boolean b73 = b72 & functionAddress73 != 0L;
        final long functionAddress74 = GLContext.getFunctionAddress("glIsEnabledi");
        this.glIsEnabledi = functionAddress74;
        final boolean b74 = b73 & functionAddress74 != 0L;
        final long functionAddress75 = GLContext.getFunctionAddress("glBindBufferRange");
        this.glBindBufferRange = functionAddress75;
        final boolean b75 = b74 & functionAddress75 != 0L;
        final long functionAddress76 = GLContext.getFunctionAddress("glBindBufferBase");
        this.glBindBufferBase = functionAddress76;
        final boolean b76 = b75 & functionAddress76 != 0L;
        final long functionAddress77 = GLContext.getFunctionAddress("glBeginTransformFeedback");
        this.glBeginTransformFeedback = functionAddress77;
        final boolean b77 = b76 & functionAddress77 != 0L;
        final long functionAddress78 = GLContext.getFunctionAddress("glEndTransformFeedback");
        this.glEndTransformFeedback = functionAddress78;
        final boolean b78 = b77 & functionAddress78 != 0L;
        final long functionAddress79 = GLContext.getFunctionAddress("glTransformFeedbackVaryings");
        this.glTransformFeedbackVaryings = functionAddress79;
        final boolean b79 = b78 & functionAddress79 != 0L;
        final long functionAddress80 = GLContext.getFunctionAddress("glGetTransformFeedbackVarying");
        this.glGetTransformFeedbackVarying = functionAddress80;
        final boolean b80 = b79 & functionAddress80 != 0L;
        final long functionAddress81 = GLContext.getFunctionAddress("glBindVertexArray");
        this.glBindVertexArray = functionAddress81;
        final boolean b81 = b80 & functionAddress81 != 0L;
        final long functionAddress82 = GLContext.getFunctionAddress("glDeleteVertexArrays");
        this.glDeleteVertexArrays = functionAddress82;
        final boolean b82 = b81 & functionAddress82 != 0L;
        final long functionAddress83 = GLContext.getFunctionAddress("glGenVertexArrays");
        this.glGenVertexArrays = functionAddress83;
        final boolean b83 = b82 & functionAddress83 != 0L;
        final long functionAddress84 = GLContext.getFunctionAddress("glIsVertexArray");
        this.glIsVertexArray = functionAddress84;
        return b83 & functionAddress84 != 0L;
    }
    
    private boolean GL31_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawArraysInstanced");
        this.glDrawArraysInstanced = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawElementsInstanced");
        this.glDrawElementsInstanced = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glCopyBufferSubData");
        this.glCopyBufferSubData = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glPrimitiveRestartIndex");
        this.glPrimitiveRestartIndex = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glTexBuffer");
        this.glTexBuffer = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetUniformIndices");
        this.glGetUniformIndices = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetActiveUniformsiv");
        this.glGetActiveUniformsiv = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetActiveUniformName");
        this.glGetActiveUniformName = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetUniformBlockIndex");
        this.glGetUniformBlockIndex = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glGetActiveUniformBlockiv");
        this.glGetActiveUniformBlockiv = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGetActiveUniformBlockName");
        this.glGetActiveUniformBlockName = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glUniformBlockBinding");
        this.glUniformBlockBinding = functionAddress12;
        return b11 & functionAddress12 != 0L;
    }
    
    private boolean GL32_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetBufferParameteri64v");
        this.glGetBufferParameteri64v = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDrawElementsBaseVertex");
        this.glDrawElementsBaseVertex = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDrawRangeElementsBaseVertex");
        this.glDrawRangeElementsBaseVertex = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glDrawElementsInstancedBaseVertex");
        this.glDrawElementsInstancedBaseVertex = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glProvokingVertex");
        this.glProvokingVertex = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glTexImage2DMultisample");
        this.glTexImage2DMultisample = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glTexImage3DMultisample");
        this.glTexImage3DMultisample = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetMultisamplefv");
        this.glGetMultisamplefv = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glSampleMaski");
        this.glSampleMaski = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glFramebufferTexture");
        this.glFramebufferTexture = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glFenceSync");
        this.glFenceSync = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glIsSync");
        this.glIsSync = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glDeleteSync");
        this.glDeleteSync = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glClientWaitSync");
        this.glClientWaitSync = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glWaitSync");
        this.glWaitSync = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glGetInteger64v");
        this.glGetInteger64v = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glGetInteger64i_v");
        this.glGetInteger64i_v = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetSynciv");
        this.glGetSynciv = functionAddress18;
        return b17 & functionAddress18 != 0L;
    }
    
    private boolean GL33_initNativeFunctionAddresses(final boolean b) {
        final long functionAddress = GLContext.getFunctionAddress("glBindFragDataLocationIndexed");
        this.glBindFragDataLocationIndexed = functionAddress;
        final boolean b2 = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetFragDataIndex");
        this.glGetFragDataIndex = functionAddress2;
        final boolean b3 = b2 & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGenSamplers");
        this.glGenSamplers = functionAddress3;
        final boolean b4 = b3 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glDeleteSamplers");
        this.glDeleteSamplers = functionAddress4;
        final boolean b5 = b4 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glIsSampler");
        this.glIsSampler = functionAddress5;
        final boolean b6 = b5 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glBindSampler");
        this.glBindSampler = functionAddress6;
        final boolean b7 = b6 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glSamplerParameteri");
        this.glSamplerParameteri = functionAddress7;
        final boolean b8 = b7 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glSamplerParameterf");
        this.glSamplerParameterf = functionAddress8;
        final boolean b9 = b8 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glSamplerParameteriv");
        this.glSamplerParameteriv = functionAddress9;
        final boolean b10 = b9 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glSamplerParameterfv");
        this.glSamplerParameterfv = functionAddress10;
        final boolean b11 = b10 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glSamplerParameterIiv");
        this.glSamplerParameterIiv = functionAddress11;
        final boolean b12 = b11 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glSamplerParameterIuiv");
        this.glSamplerParameterIuiv = functionAddress12;
        final boolean b13 = b12 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glGetSamplerParameteriv");
        this.glGetSamplerParameteriv = functionAddress13;
        final boolean b14 = b13 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glGetSamplerParameterfv");
        this.glGetSamplerParameterfv = functionAddress14;
        final boolean b15 = b14 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glGetSamplerParameterIiv");
        this.glGetSamplerParameterIiv = functionAddress15;
        final boolean b16 = b15 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glGetSamplerParameterIuiv");
        this.glGetSamplerParameterIuiv = functionAddress16;
        final boolean b17 = b16 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glQueryCounter");
        this.glQueryCounter = functionAddress17;
        final boolean b18 = b17 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetQueryObjecti64v");
        this.glGetQueryObjecti64v = functionAddress18;
        final boolean b19 = b18 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glGetQueryObjectui64v");
        this.glGetQueryObjectui64v = functionAddress19;
        final boolean b20 = b19 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glVertexAttribDivisor");
        this.glVertexAttribDivisor = functionAddress20;
        final boolean b21 = b20 & functionAddress20 != 0L;
        boolean b22 = false;
        Label_0464: {
            if (!b) {
                final long functionAddress21 = GLContext.getFunctionAddress("glVertexP2ui");
                this.glVertexP2ui = functionAddress21;
                if (functionAddress21 == 0L) {
                    b22 = false;
                    break Label_0464;
                }
            }
            b22 = true;
        }
        final boolean b23 = b21 & b22;
        boolean b24 = false;
        Label_0490: {
            if (!b) {
                final long functionAddress22 = GLContext.getFunctionAddress("glVertexP3ui");
                this.glVertexP3ui = functionAddress22;
                if (functionAddress22 == 0L) {
                    b24 = false;
                    break Label_0490;
                }
            }
            b24 = true;
        }
        final boolean b25 = b23 & b24;
        boolean b26 = false;
        Label_0516: {
            if (!b) {
                final long functionAddress23 = GLContext.getFunctionAddress("glVertexP4ui");
                this.glVertexP4ui = functionAddress23;
                if (functionAddress23 == 0L) {
                    b26 = false;
                    break Label_0516;
                }
            }
            b26 = true;
        }
        final boolean b27 = b25 & b26;
        boolean b28 = false;
        Label_0542: {
            if (!b) {
                final long functionAddress24 = GLContext.getFunctionAddress("glVertexP2uiv");
                this.glVertexP2uiv = functionAddress24;
                if (functionAddress24 == 0L) {
                    b28 = false;
                    break Label_0542;
                }
            }
            b28 = true;
        }
        final boolean b29 = b27 & b28;
        boolean b30 = false;
        Label_0568: {
            if (!b) {
                final long functionAddress25 = GLContext.getFunctionAddress("glVertexP3uiv");
                this.glVertexP3uiv = functionAddress25;
                if (functionAddress25 == 0L) {
                    b30 = false;
                    break Label_0568;
                }
            }
            b30 = true;
        }
        final boolean b31 = b29 & b30;
        boolean b32 = false;
        Label_0594: {
            if (!b) {
                final long functionAddress26 = GLContext.getFunctionAddress("glVertexP4uiv");
                this.glVertexP4uiv = functionAddress26;
                if (functionAddress26 == 0L) {
                    b32 = false;
                    break Label_0594;
                }
            }
            b32 = true;
        }
        final boolean b33 = b31 & b32;
        boolean b34 = false;
        Label_0620: {
            if (!b) {
                final long functionAddress27 = GLContext.getFunctionAddress("glTexCoordP1ui");
                this.glTexCoordP1ui = functionAddress27;
                if (functionAddress27 == 0L) {
                    b34 = false;
                    break Label_0620;
                }
            }
            b34 = true;
        }
        final boolean b35 = b33 & b34;
        boolean b36 = false;
        Label_0646: {
            if (!b) {
                final long functionAddress28 = GLContext.getFunctionAddress("glTexCoordP2ui");
                this.glTexCoordP2ui = functionAddress28;
                if (functionAddress28 == 0L) {
                    b36 = false;
                    break Label_0646;
                }
            }
            b36 = true;
        }
        final boolean b37 = b35 & b36;
        boolean b38 = false;
        Label_0672: {
            if (!b) {
                final long functionAddress29 = GLContext.getFunctionAddress("glTexCoordP3ui");
                this.glTexCoordP3ui = functionAddress29;
                if (functionAddress29 == 0L) {
                    b38 = false;
                    break Label_0672;
                }
            }
            b38 = true;
        }
        final boolean b39 = b37 & b38;
        boolean b40 = false;
        Label_0698: {
            if (!b) {
                final long functionAddress30 = GLContext.getFunctionAddress("glTexCoordP4ui");
                this.glTexCoordP4ui = functionAddress30;
                if (functionAddress30 == 0L) {
                    b40 = false;
                    break Label_0698;
                }
            }
            b40 = true;
        }
        final boolean b41 = b39 & b40;
        boolean b42 = false;
        Label_0724: {
            if (!b) {
                final long functionAddress31 = GLContext.getFunctionAddress("glTexCoordP1uiv");
                this.glTexCoordP1uiv = functionAddress31;
                if (functionAddress31 == 0L) {
                    b42 = false;
                    break Label_0724;
                }
            }
            b42 = true;
        }
        final boolean b43 = b41 & b42;
        boolean b44 = false;
        Label_0750: {
            if (!b) {
                final long functionAddress32 = GLContext.getFunctionAddress("glTexCoordP2uiv");
                this.glTexCoordP2uiv = functionAddress32;
                if (functionAddress32 == 0L) {
                    b44 = false;
                    break Label_0750;
                }
            }
            b44 = true;
        }
        final boolean b45 = b43 & b44;
        boolean b46 = false;
        Label_0776: {
            if (!b) {
                final long functionAddress33 = GLContext.getFunctionAddress("glTexCoordP3uiv");
                this.glTexCoordP3uiv = functionAddress33;
                if (functionAddress33 == 0L) {
                    b46 = false;
                    break Label_0776;
                }
            }
            b46 = true;
        }
        final boolean b47 = b45 & b46;
        boolean b48 = false;
        Label_0802: {
            if (!b) {
                final long functionAddress34 = GLContext.getFunctionAddress("glTexCoordP4uiv");
                this.glTexCoordP4uiv = functionAddress34;
                if (functionAddress34 == 0L) {
                    b48 = false;
                    break Label_0802;
                }
            }
            b48 = true;
        }
        final boolean b49 = b47 & b48;
        boolean b50 = false;
        Label_0828: {
            if (!b) {
                final long functionAddress35 = GLContext.getFunctionAddress("glMultiTexCoordP1ui");
                this.glMultiTexCoordP1ui = functionAddress35;
                if (functionAddress35 == 0L) {
                    b50 = false;
                    break Label_0828;
                }
            }
            b50 = true;
        }
        final boolean b51 = b49 & b50;
        boolean b52 = false;
        Label_0854: {
            if (!b) {
                final long functionAddress36 = GLContext.getFunctionAddress("glMultiTexCoordP2ui");
                this.glMultiTexCoordP2ui = functionAddress36;
                if (functionAddress36 == 0L) {
                    b52 = false;
                    break Label_0854;
                }
            }
            b52 = true;
        }
        final boolean b53 = b51 & b52;
        boolean b54 = false;
        Label_0880: {
            if (!b) {
                final long functionAddress37 = GLContext.getFunctionAddress("glMultiTexCoordP3ui");
                this.glMultiTexCoordP3ui = functionAddress37;
                if (functionAddress37 == 0L) {
                    b54 = false;
                    break Label_0880;
                }
            }
            b54 = true;
        }
        final boolean b55 = b53 & b54;
        boolean b56 = false;
        Label_0906: {
            if (!b) {
                final long functionAddress38 = GLContext.getFunctionAddress("glMultiTexCoordP4ui");
                this.glMultiTexCoordP4ui = functionAddress38;
                if (functionAddress38 == 0L) {
                    b56 = false;
                    break Label_0906;
                }
            }
            b56 = true;
        }
        final boolean b57 = b55 & b56;
        boolean b58 = false;
        Label_0932: {
            if (!b) {
                final long functionAddress39 = GLContext.getFunctionAddress("glMultiTexCoordP1uiv");
                this.glMultiTexCoordP1uiv = functionAddress39;
                if (functionAddress39 == 0L) {
                    b58 = false;
                    break Label_0932;
                }
            }
            b58 = true;
        }
        final boolean b59 = b57 & b58;
        boolean b60 = false;
        Label_0958: {
            if (!b) {
                final long functionAddress40 = GLContext.getFunctionAddress("glMultiTexCoordP2uiv");
                this.glMultiTexCoordP2uiv = functionAddress40;
                if (functionAddress40 == 0L) {
                    b60 = false;
                    break Label_0958;
                }
            }
            b60 = true;
        }
        final boolean b61 = b59 & b60;
        boolean b62 = false;
        Label_0984: {
            if (!b) {
                final long functionAddress41 = GLContext.getFunctionAddress("glMultiTexCoordP3uiv");
                this.glMultiTexCoordP3uiv = functionAddress41;
                if (functionAddress41 == 0L) {
                    b62 = false;
                    break Label_0984;
                }
            }
            b62 = true;
        }
        final boolean b63 = b61 & b62;
        boolean b64 = false;
        Label_1010: {
            if (!b) {
                final long functionAddress42 = GLContext.getFunctionAddress("glMultiTexCoordP4uiv");
                this.glMultiTexCoordP4uiv = functionAddress42;
                if (functionAddress42 == 0L) {
                    b64 = false;
                    break Label_1010;
                }
            }
            b64 = true;
        }
        final boolean b65 = b63 & b64;
        boolean b66 = false;
        Label_1036: {
            if (!b) {
                final long functionAddress43 = GLContext.getFunctionAddress("glNormalP3ui");
                this.glNormalP3ui = functionAddress43;
                if (functionAddress43 == 0L) {
                    b66 = false;
                    break Label_1036;
                }
            }
            b66 = true;
        }
        final boolean b67 = b65 & b66;
        boolean b68 = false;
        Label_1062: {
            if (!b) {
                final long functionAddress44 = GLContext.getFunctionAddress("glNormalP3uiv");
                this.glNormalP3uiv = functionAddress44;
                if (functionAddress44 == 0L) {
                    b68 = false;
                    break Label_1062;
                }
            }
            b68 = true;
        }
        final boolean b69 = b67 & b68;
        boolean b70 = false;
        Label_1088: {
            if (!b) {
                final long functionAddress45 = GLContext.getFunctionAddress("glColorP3ui");
                this.glColorP3ui = functionAddress45;
                if (functionAddress45 == 0L) {
                    b70 = false;
                    break Label_1088;
                }
            }
            b70 = true;
        }
        final boolean b71 = b69 & b70;
        boolean b72 = false;
        Label_1114: {
            if (!b) {
                final long functionAddress46 = GLContext.getFunctionAddress("glColorP4ui");
                this.glColorP4ui = functionAddress46;
                if (functionAddress46 == 0L) {
                    b72 = false;
                    break Label_1114;
                }
            }
            b72 = true;
        }
        final boolean b73 = b71 & b72;
        boolean b74 = false;
        Label_1140: {
            if (!b) {
                final long functionAddress47 = GLContext.getFunctionAddress("glColorP3uiv");
                this.glColorP3uiv = functionAddress47;
                if (functionAddress47 == 0L) {
                    b74 = false;
                    break Label_1140;
                }
            }
            b74 = true;
        }
        final boolean b75 = b73 & b74;
        boolean b76 = false;
        Label_1166: {
            if (!b) {
                final long functionAddress48 = GLContext.getFunctionAddress("glColorP4uiv");
                this.glColorP4uiv = functionAddress48;
                if (functionAddress48 == 0L) {
                    b76 = false;
                    break Label_1166;
                }
            }
            b76 = true;
        }
        final boolean b77 = b75 & b76;
        boolean b78 = false;
        Label_1192: {
            if (!b) {
                final long functionAddress49 = GLContext.getFunctionAddress("glSecondaryColorP3ui");
                this.glSecondaryColorP3ui = functionAddress49;
                if (functionAddress49 == 0L) {
                    b78 = false;
                    break Label_1192;
                }
            }
            b78 = true;
        }
        final boolean b79 = b77 & b78;
        boolean b80 = false;
        Label_1218: {
            if (!b) {
                final long functionAddress50 = GLContext.getFunctionAddress("glSecondaryColorP3uiv");
                this.glSecondaryColorP3uiv = functionAddress50;
                if (functionAddress50 == 0L) {
                    b80 = false;
                    break Label_1218;
                }
            }
            b80 = true;
        }
        final boolean b81 = b79 & b80;
        boolean b82 = false;
        Label_1244: {
            if (!b) {
                final long functionAddress51 = GLContext.getFunctionAddress("glVertexAttribP1ui");
                this.glVertexAttribP1ui = functionAddress51;
                if (functionAddress51 == 0L) {
                    b82 = false;
                    break Label_1244;
                }
            }
            b82 = true;
        }
        final boolean b83 = b81 & b82;
        boolean b84 = false;
        Label_1270: {
            if (!b) {
                final long functionAddress52 = GLContext.getFunctionAddress("glVertexAttribP2ui");
                this.glVertexAttribP2ui = functionAddress52;
                if (functionAddress52 == 0L) {
                    b84 = false;
                    break Label_1270;
                }
            }
            b84 = true;
        }
        final boolean b85 = b83 & b84;
        boolean b86 = false;
        Label_1296: {
            if (!b) {
                final long functionAddress53 = GLContext.getFunctionAddress("glVertexAttribP3ui");
                this.glVertexAttribP3ui = functionAddress53;
                if (functionAddress53 == 0L) {
                    b86 = false;
                    break Label_1296;
                }
            }
            b86 = true;
        }
        final boolean b87 = b85 & b86;
        boolean b88 = false;
        Label_1322: {
            if (!b) {
                final long functionAddress54 = GLContext.getFunctionAddress("glVertexAttribP4ui");
                this.glVertexAttribP4ui = functionAddress54;
                if (functionAddress54 == 0L) {
                    b88 = false;
                    break Label_1322;
                }
            }
            b88 = true;
        }
        final boolean b89 = b87 & b88;
        boolean b90 = false;
        Label_1348: {
            if (!b) {
                final long functionAddress55 = GLContext.getFunctionAddress("glVertexAttribP1uiv");
                this.glVertexAttribP1uiv = functionAddress55;
                if (functionAddress55 == 0L) {
                    b90 = false;
                    break Label_1348;
                }
            }
            b90 = true;
        }
        final boolean b91 = b89 & b90;
        boolean b92 = false;
        Label_1374: {
            if (!b) {
                final long functionAddress56 = GLContext.getFunctionAddress("glVertexAttribP2uiv");
                this.glVertexAttribP2uiv = functionAddress56;
                if (functionAddress56 == 0L) {
                    b92 = false;
                    break Label_1374;
                }
            }
            b92 = true;
        }
        final boolean b93 = b91 & b92;
        boolean b94 = false;
        Label_1400: {
            if (!b) {
                final long functionAddress57 = GLContext.getFunctionAddress("glVertexAttribP3uiv");
                this.glVertexAttribP3uiv = functionAddress57;
                if (functionAddress57 == 0L) {
                    b94 = false;
                    break Label_1400;
                }
            }
            b94 = true;
        }
        final boolean b95 = b93 & b94;
        if (!b) {
            final long functionAddress58 = GLContext.getFunctionAddress("glVertexAttribP4uiv");
            this.glVertexAttribP4uiv = functionAddress58;
            if (functionAddress58 == 0L) {
                final boolean b96 = false;
                return b95 & b96;
            }
        }
        final boolean b96 = true;
        return b95 & b96;
    }
    
    private boolean GL40_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBlendEquationi");
        this.glBlendEquationi = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBlendEquationSeparatei");
        this.glBlendEquationSeparatei = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glBlendFunci");
        this.glBlendFunci = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBlendFuncSeparatei");
        this.glBlendFuncSeparatei = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glDrawArraysIndirect");
        this.glDrawArraysIndirect = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glDrawElementsIndirect");
        this.glDrawElementsIndirect = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glUniform1d");
        this.glUniform1d = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glUniform2d");
        this.glUniform2d = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glUniform3d");
        this.glUniform3d = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glUniform4d");
        this.glUniform4d = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glUniform1dv");
        this.glUniform1dv = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glUniform2dv");
        this.glUniform2dv = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glUniform3dv");
        this.glUniform3dv = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glUniform4dv");
        this.glUniform4dv = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glUniformMatrix2dv");
        this.glUniformMatrix2dv = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glUniformMatrix3dv");
        this.glUniformMatrix3dv = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glUniformMatrix4dv");
        this.glUniformMatrix4dv = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glUniformMatrix2x3dv");
        this.glUniformMatrix2x3dv = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glUniformMatrix2x4dv");
        this.glUniformMatrix2x4dv = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glUniformMatrix3x2dv");
        this.glUniformMatrix3x2dv = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glUniformMatrix3x4dv");
        this.glUniformMatrix3x4dv = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glUniformMatrix4x2dv");
        this.glUniformMatrix4x2dv = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glUniformMatrix4x3dv");
        this.glUniformMatrix4x3dv = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glGetUniformdv");
        this.glGetUniformdv = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glMinSampleShading");
        this.glMinSampleShading = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glGetSubroutineUniformLocation");
        this.glGetSubroutineUniformLocation = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glGetSubroutineIndex");
        this.glGetSubroutineIndex = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glGetActiveSubroutineUniformiv");
        this.glGetActiveSubroutineUniformiv = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glGetActiveSubroutineUniformName");
        this.glGetActiveSubroutineUniformName = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glGetActiveSubroutineName");
        this.glGetActiveSubroutineName = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glUniformSubroutinesuiv");
        this.glUniformSubroutinesuiv = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glGetUniformSubroutineuiv");
        this.glGetUniformSubroutineuiv = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glGetProgramStageiv");
        this.glGetProgramStageiv = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glPatchParameteri");
        this.glPatchParameteri = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glPatchParameterfv");
        this.glPatchParameterfv = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glBindTransformFeedback");
        this.glBindTransformFeedback = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glDeleteTransformFeedbacks");
        this.glDeleteTransformFeedbacks = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glGenTransformFeedbacks");
        this.glGenTransformFeedbacks = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glIsTransformFeedback");
        this.glIsTransformFeedback = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glPauseTransformFeedback");
        this.glPauseTransformFeedback = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glResumeTransformFeedback");
        this.glResumeTransformFeedback = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glDrawTransformFeedback");
        this.glDrawTransformFeedback = functionAddress42;
        final boolean b42 = b41 & functionAddress42 != 0L;
        final long functionAddress43 = GLContext.getFunctionAddress("glDrawTransformFeedbackStream");
        this.glDrawTransformFeedbackStream = functionAddress43;
        final boolean b43 = b42 & functionAddress43 != 0L;
        final long functionAddress44 = GLContext.getFunctionAddress("glBeginQueryIndexed");
        this.glBeginQueryIndexed = functionAddress44;
        final boolean b44 = b43 & functionAddress44 != 0L;
        final long functionAddress45 = GLContext.getFunctionAddress("glEndQueryIndexed");
        this.glEndQueryIndexed = functionAddress45;
        final boolean b45 = b44 & functionAddress45 != 0L;
        final long functionAddress46 = GLContext.getFunctionAddress("glGetQueryIndexediv");
        this.glGetQueryIndexediv = functionAddress46;
        return b45 & functionAddress46 != 0L;
    }
    
    private boolean GL41_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glReleaseShaderCompiler");
        this.glReleaseShaderCompiler = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glShaderBinary");
        this.glShaderBinary = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetShaderPrecisionFormat");
        this.glGetShaderPrecisionFormat = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glDepthRangef");
        this.glDepthRangef = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glClearDepthf");
        this.glClearDepthf = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetProgramBinary");
        this.glGetProgramBinary = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glProgramBinary");
        this.glProgramBinary = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glProgramParameteri");
        this.glProgramParameteri = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glUseProgramStages");
        this.glUseProgramStages = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glActiveShaderProgram");
        this.glActiveShaderProgram = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glCreateShaderProgramv");
        this.glCreateShaderProgramv = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glBindProgramPipeline");
        this.glBindProgramPipeline = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glDeleteProgramPipelines");
        this.glDeleteProgramPipelines = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glGenProgramPipelines");
        this.glGenProgramPipelines = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glIsProgramPipeline");
        this.glIsProgramPipeline = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glGetProgramPipelineiv");
        this.glGetProgramPipelineiv = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glProgramUniform1i");
        this.glProgramUniform1i = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glProgramUniform2i");
        this.glProgramUniform2i = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glProgramUniform3i");
        this.glProgramUniform3i = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glProgramUniform4i");
        this.glProgramUniform4i = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glProgramUniform1f");
        this.glProgramUniform1f = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glProgramUniform2f");
        this.glProgramUniform2f = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glProgramUniform3f");
        this.glProgramUniform3f = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glProgramUniform4f");
        this.glProgramUniform4f = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glProgramUniform1d");
        this.glProgramUniform1d = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glProgramUniform2d");
        this.glProgramUniform2d = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glProgramUniform3d");
        this.glProgramUniform3d = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glProgramUniform4d");
        this.glProgramUniform4d = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glProgramUniform1iv");
        this.glProgramUniform1iv = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glProgramUniform2iv");
        this.glProgramUniform2iv = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glProgramUniform3iv");
        this.glProgramUniform3iv = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glProgramUniform4iv");
        this.glProgramUniform4iv = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glProgramUniform1fv");
        this.glProgramUniform1fv = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glProgramUniform2fv");
        this.glProgramUniform2fv = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glProgramUniform3fv");
        this.glProgramUniform3fv = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glProgramUniform4fv");
        this.glProgramUniform4fv = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glProgramUniform1dv");
        this.glProgramUniform1dv = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glProgramUniform2dv");
        this.glProgramUniform2dv = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glProgramUniform3dv");
        this.glProgramUniform3dv = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glProgramUniform4dv");
        this.glProgramUniform4dv = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glProgramUniform1ui");
        this.glProgramUniform1ui = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glProgramUniform2ui");
        this.glProgramUniform2ui = functionAddress42;
        final boolean b42 = b41 & functionAddress42 != 0L;
        final long functionAddress43 = GLContext.getFunctionAddress("glProgramUniform3ui");
        this.glProgramUniform3ui = functionAddress43;
        final boolean b43 = b42 & functionAddress43 != 0L;
        final long functionAddress44 = GLContext.getFunctionAddress("glProgramUniform4ui");
        this.glProgramUniform4ui = functionAddress44;
        final boolean b44 = b43 & functionAddress44 != 0L;
        final long functionAddress45 = GLContext.getFunctionAddress("glProgramUniform1uiv");
        this.glProgramUniform1uiv = functionAddress45;
        final boolean b45 = b44 & functionAddress45 != 0L;
        final long functionAddress46 = GLContext.getFunctionAddress("glProgramUniform2uiv");
        this.glProgramUniform2uiv = functionAddress46;
        final boolean b46 = b45 & functionAddress46 != 0L;
        final long functionAddress47 = GLContext.getFunctionAddress("glProgramUniform3uiv");
        this.glProgramUniform3uiv = functionAddress47;
        final boolean b47 = b46 & functionAddress47 != 0L;
        final long functionAddress48 = GLContext.getFunctionAddress("glProgramUniform4uiv");
        this.glProgramUniform4uiv = functionAddress48;
        final boolean b48 = b47 & functionAddress48 != 0L;
        final long functionAddress49 = GLContext.getFunctionAddress("glProgramUniformMatrix2fv");
        this.glProgramUniformMatrix2fv = functionAddress49;
        final boolean b49 = b48 & functionAddress49 != 0L;
        final long functionAddress50 = GLContext.getFunctionAddress("glProgramUniformMatrix3fv");
        this.glProgramUniformMatrix3fv = functionAddress50;
        final boolean b50 = b49 & functionAddress50 != 0L;
        final long functionAddress51 = GLContext.getFunctionAddress("glProgramUniformMatrix4fv");
        this.glProgramUniformMatrix4fv = functionAddress51;
        final boolean b51 = b50 & functionAddress51 != 0L;
        final long functionAddress52 = GLContext.getFunctionAddress("glProgramUniformMatrix2dv");
        this.glProgramUniformMatrix2dv = functionAddress52;
        final boolean b52 = b51 & functionAddress52 != 0L;
        final long functionAddress53 = GLContext.getFunctionAddress("glProgramUniformMatrix3dv");
        this.glProgramUniformMatrix3dv = functionAddress53;
        final boolean b53 = b52 & functionAddress53 != 0L;
        final long functionAddress54 = GLContext.getFunctionAddress("glProgramUniformMatrix4dv");
        this.glProgramUniformMatrix4dv = functionAddress54;
        final boolean b54 = b53 & functionAddress54 != 0L;
        final long functionAddress55 = GLContext.getFunctionAddress("glProgramUniformMatrix2x3fv");
        this.glProgramUniformMatrix2x3fv = functionAddress55;
        final boolean b55 = b54 & functionAddress55 != 0L;
        final long functionAddress56 = GLContext.getFunctionAddress("glProgramUniformMatrix3x2fv");
        this.glProgramUniformMatrix3x2fv = functionAddress56;
        final boolean b56 = b55 & functionAddress56 != 0L;
        final long functionAddress57 = GLContext.getFunctionAddress("glProgramUniformMatrix2x4fv");
        this.glProgramUniformMatrix2x4fv = functionAddress57;
        final boolean b57 = b56 & functionAddress57 != 0L;
        final long functionAddress58 = GLContext.getFunctionAddress("glProgramUniformMatrix4x2fv");
        this.glProgramUniformMatrix4x2fv = functionAddress58;
        final boolean b58 = b57 & functionAddress58 != 0L;
        final long functionAddress59 = GLContext.getFunctionAddress("glProgramUniformMatrix3x4fv");
        this.glProgramUniformMatrix3x4fv = functionAddress59;
        final boolean b59 = b58 & functionAddress59 != 0L;
        final long functionAddress60 = GLContext.getFunctionAddress("glProgramUniformMatrix4x3fv");
        this.glProgramUniformMatrix4x3fv = functionAddress60;
        final boolean b60 = b59 & functionAddress60 != 0L;
        final long functionAddress61 = GLContext.getFunctionAddress("glProgramUniformMatrix2x3dv");
        this.glProgramUniformMatrix2x3dv = functionAddress61;
        final boolean b61 = b60 & functionAddress61 != 0L;
        final long functionAddress62 = GLContext.getFunctionAddress("glProgramUniformMatrix3x2dv");
        this.glProgramUniformMatrix3x2dv = functionAddress62;
        final boolean b62 = b61 & functionAddress62 != 0L;
        final long functionAddress63 = GLContext.getFunctionAddress("glProgramUniformMatrix2x4dv");
        this.glProgramUniformMatrix2x4dv = functionAddress63;
        final boolean b63 = b62 & functionAddress63 != 0L;
        final long functionAddress64 = GLContext.getFunctionAddress("glProgramUniformMatrix4x2dv");
        this.glProgramUniformMatrix4x2dv = functionAddress64;
        final boolean b64 = b63 & functionAddress64 != 0L;
        final long functionAddress65 = GLContext.getFunctionAddress("glProgramUniformMatrix3x4dv");
        this.glProgramUniformMatrix3x4dv = functionAddress65;
        final boolean b65 = b64 & functionAddress65 != 0L;
        final long functionAddress66 = GLContext.getFunctionAddress("glProgramUniformMatrix4x3dv");
        this.glProgramUniformMatrix4x3dv = functionAddress66;
        final boolean b66 = b65 & functionAddress66 != 0L;
        final long functionAddress67 = GLContext.getFunctionAddress("glValidateProgramPipeline");
        this.glValidateProgramPipeline = functionAddress67;
        final boolean b67 = b66 & functionAddress67 != 0L;
        final long functionAddress68 = GLContext.getFunctionAddress("glGetProgramPipelineInfoLog");
        this.glGetProgramPipelineInfoLog = functionAddress68;
        final boolean b68 = b67 & functionAddress68 != 0L;
        final long functionAddress69 = GLContext.getFunctionAddress("glVertexAttribL1d");
        this.glVertexAttribL1d = functionAddress69;
        final boolean b69 = b68 & functionAddress69 != 0L;
        final long functionAddress70 = GLContext.getFunctionAddress("glVertexAttribL2d");
        this.glVertexAttribL2d = functionAddress70;
        final boolean b70 = b69 & functionAddress70 != 0L;
        final long functionAddress71 = GLContext.getFunctionAddress("glVertexAttribL3d");
        this.glVertexAttribL3d = functionAddress71;
        final boolean b71 = b70 & functionAddress71 != 0L;
        final long functionAddress72 = GLContext.getFunctionAddress("glVertexAttribL4d");
        this.glVertexAttribL4d = functionAddress72;
        final boolean b72 = b71 & functionAddress72 != 0L;
        final long functionAddress73 = GLContext.getFunctionAddress("glVertexAttribL1dv");
        this.glVertexAttribL1dv = functionAddress73;
        final boolean b73 = b72 & functionAddress73 != 0L;
        final long functionAddress74 = GLContext.getFunctionAddress("glVertexAttribL2dv");
        this.glVertexAttribL2dv = functionAddress74;
        final boolean b74 = b73 & functionAddress74 != 0L;
        final long functionAddress75 = GLContext.getFunctionAddress("glVertexAttribL3dv");
        this.glVertexAttribL3dv = functionAddress75;
        final boolean b75 = b74 & functionAddress75 != 0L;
        final long functionAddress76 = GLContext.getFunctionAddress("glVertexAttribL4dv");
        this.glVertexAttribL4dv = functionAddress76;
        final boolean b76 = b75 & functionAddress76 != 0L;
        final long functionAddress77 = GLContext.getFunctionAddress("glVertexAttribLPointer");
        this.glVertexAttribLPointer = functionAddress77;
        final boolean b77 = b76 & functionAddress77 != 0L;
        final long functionAddress78 = GLContext.getFunctionAddress("glGetVertexAttribLdv");
        this.glGetVertexAttribLdv = functionAddress78;
        final boolean b78 = b77 & functionAddress78 != 0L;
        final long functionAddress79 = GLContext.getFunctionAddress("glViewportArrayv");
        this.glViewportArrayv = functionAddress79;
        final boolean b79 = b78 & functionAddress79 != 0L;
        final long functionAddress80 = GLContext.getFunctionAddress("glViewportIndexedf");
        this.glViewportIndexedf = functionAddress80;
        final boolean b80 = b79 & functionAddress80 != 0L;
        final long functionAddress81 = GLContext.getFunctionAddress("glViewportIndexedfv");
        this.glViewportIndexedfv = functionAddress81;
        final boolean b81 = b80 & functionAddress81 != 0L;
        final long functionAddress82 = GLContext.getFunctionAddress("glScissorArrayv");
        this.glScissorArrayv = functionAddress82;
        final boolean b82 = b81 & functionAddress82 != 0L;
        final long functionAddress83 = GLContext.getFunctionAddress("glScissorIndexed");
        this.glScissorIndexed = functionAddress83;
        final boolean b83 = b82 & functionAddress83 != 0L;
        final long functionAddress84 = GLContext.getFunctionAddress("glScissorIndexedv");
        this.glScissorIndexedv = functionAddress84;
        final boolean b84 = b83 & functionAddress84 != 0L;
        final long functionAddress85 = GLContext.getFunctionAddress("glDepthRangeArrayv");
        this.glDepthRangeArrayv = functionAddress85;
        final boolean b85 = b84 & functionAddress85 != 0L;
        final long functionAddress86 = GLContext.getFunctionAddress("glDepthRangeIndexed");
        this.glDepthRangeIndexed = functionAddress86;
        final boolean b86 = b85 & functionAddress86 != 0L;
        final long functionAddress87 = GLContext.getFunctionAddress("glGetFloati_v");
        this.glGetFloati_v = functionAddress87;
        final boolean b87 = b86 & functionAddress87 != 0L;
        final long functionAddress88 = GLContext.getFunctionAddress("glGetDoublei_v");
        this.glGetDoublei_v = functionAddress88;
        return b87 & functionAddress88 != 0L;
    }
    
    private boolean GL42_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetActiveAtomicCounterBufferiv");
        this.glGetActiveAtomicCounterBufferiv = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glTexStorage1D");
        this.glTexStorage1D = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glTexStorage2D");
        this.glTexStorage2D = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glTexStorage3D");
        this.glTexStorage3D = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glDrawTransformFeedbackInstanced");
        this.glDrawTransformFeedbackInstanced = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glDrawTransformFeedbackStreamInstanced");
        this.glDrawTransformFeedbackStreamInstanced = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glDrawArraysInstancedBaseInstance");
        this.glDrawArraysInstancedBaseInstance = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glDrawElementsInstancedBaseInstance");
        this.glDrawElementsInstancedBaseInstance = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glDrawElementsInstancedBaseVertexBaseInstance");
        this.glDrawElementsInstancedBaseVertexBaseInstance = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glBindImageTexture");
        this.glBindImageTexture = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glMemoryBarrier");
        this.glMemoryBarrier = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glGetInternalformativ");
        this.glGetInternalformativ = functionAddress12;
        return b11 & functionAddress12 != 0L;
    }
    
    private boolean GL43_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glClearBufferData");
        this.glClearBufferData = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glClearBufferSubData");
        this.glClearBufferSubData = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDispatchCompute");
        this.glDispatchCompute = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glDispatchComputeIndirect");
        this.glDispatchComputeIndirect = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glCopyImageSubData");
        this.glCopyImageSubData = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glDebugMessageControl");
        this.glDebugMessageControl = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glDebugMessageInsert");
        this.glDebugMessageInsert = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glDebugMessageCallback");
        this.glDebugMessageCallback = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetDebugMessageLog");
        this.glGetDebugMessageLog = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glPushDebugGroup");
        this.glPushDebugGroup = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glPopDebugGroup");
        this.glPopDebugGroup = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glObjectLabel");
        this.glObjectLabel = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glGetObjectLabel");
        this.glGetObjectLabel = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glObjectPtrLabel");
        this.glObjectPtrLabel = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glGetObjectPtrLabel");
        this.glGetObjectPtrLabel = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glFramebufferParameteri");
        this.glFramebufferParameteri = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glGetFramebufferParameteriv");
        this.glGetFramebufferParameteriv = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetInternalformati64v");
        this.glGetInternalformati64v = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glInvalidateTexSubImage");
        this.glInvalidateTexSubImage = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glInvalidateTexImage");
        this.glInvalidateTexImage = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glInvalidateBufferSubData");
        this.glInvalidateBufferSubData = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glInvalidateBufferData");
        this.glInvalidateBufferData = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glInvalidateFramebuffer");
        this.glInvalidateFramebuffer = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glInvalidateSubFramebuffer");
        this.glInvalidateSubFramebuffer = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glMultiDrawArraysIndirect");
        this.glMultiDrawArraysIndirect = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glMultiDrawElementsIndirect");
        this.glMultiDrawElementsIndirect = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glGetProgramInterfaceiv");
        this.glGetProgramInterfaceiv = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glGetProgramResourceIndex");
        this.glGetProgramResourceIndex = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glGetProgramResourceName");
        this.glGetProgramResourceName = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glGetProgramResourceiv");
        this.glGetProgramResourceiv = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glGetProgramResourceLocation");
        this.glGetProgramResourceLocation = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glGetProgramResourceLocationIndex");
        this.glGetProgramResourceLocationIndex = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glShaderStorageBlockBinding");
        this.glShaderStorageBlockBinding = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glTexBufferRange");
        this.glTexBufferRange = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glTexStorage2DMultisample");
        this.glTexStorage2DMultisample = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glTexStorage3DMultisample");
        this.glTexStorage3DMultisample = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glTextureView");
        this.glTextureView = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glBindVertexBuffer");
        this.glBindVertexBuffer = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glVertexAttribFormat");
        this.glVertexAttribFormat = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glVertexAttribIFormat");
        this.glVertexAttribIFormat = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glVertexAttribLFormat");
        this.glVertexAttribLFormat = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glVertexAttribBinding");
        this.glVertexAttribBinding = functionAddress42;
        final boolean b42 = b41 & functionAddress42 != 0L;
        final long functionAddress43 = GLContext.getFunctionAddress("glVertexBindingDivisor");
        this.glVertexBindingDivisor = functionAddress43;
        return b42 & functionAddress43 != 0L;
    }
    
    private boolean GL44_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBufferStorage");
        this.glBufferStorage = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glClearTexImage");
        this.glClearTexImage = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glClearTexSubImage");
        this.glClearTexSubImage = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBindBuffersBase");
        this.glBindBuffersBase = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glBindBuffersRange");
        this.glBindBuffersRange = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glBindTextures");
        this.glBindTextures = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glBindSamplers");
        this.glBindSamplers = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glBindImageTextures");
        this.glBindImageTextures = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glBindVertexBuffers");
        this.glBindVertexBuffers = functionAddress9;
        return b8 & functionAddress9 != 0L;
    }
    
    private boolean GL45_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glClipControl");
        this.glClipControl = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glCreateTransformFeedbacks");
        this.glCreateTransformFeedbacks = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glTransformFeedbackBufferBase");
        this.glTransformFeedbackBufferBase = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glTransformFeedbackBufferRange");
        this.glTransformFeedbackBufferRange = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetTransformFeedbackiv");
        this.glGetTransformFeedbackiv = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetTransformFeedbacki_v");
        this.glGetTransformFeedbacki_v = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetTransformFeedbacki64_v");
        this.glGetTransformFeedbacki64_v = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glCreateBuffers");
        this.glCreateBuffers = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glNamedBufferStorage");
        this.glNamedBufferStorage = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glNamedBufferData");
        this.glNamedBufferData = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glNamedBufferSubData");
        this.glNamedBufferSubData = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glCopyNamedBufferSubData");
        this.glCopyNamedBufferSubData = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glClearNamedBufferData");
        this.glClearNamedBufferData = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glClearNamedBufferSubData");
        this.glClearNamedBufferSubData = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glMapNamedBuffer");
        this.glMapNamedBuffer = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glMapNamedBufferRange");
        this.glMapNamedBufferRange = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glUnmapNamedBuffer");
        this.glUnmapNamedBuffer = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glFlushMappedNamedBufferRange");
        this.glFlushMappedNamedBufferRange = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glGetNamedBufferParameteriv");
        this.glGetNamedBufferParameteriv = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glGetNamedBufferParameteri64v");
        this.glGetNamedBufferParameteri64v = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glGetNamedBufferPointerv");
        this.glGetNamedBufferPointerv = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glGetNamedBufferSubData");
        this.glGetNamedBufferSubData = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glCreateFramebuffers");
        this.glCreateFramebuffers = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glNamedFramebufferRenderbuffer");
        this.glNamedFramebufferRenderbuffer = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glNamedFramebufferParameteri");
        this.glNamedFramebufferParameteri = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glNamedFramebufferTexture");
        this.glNamedFramebufferTexture = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glNamedFramebufferTextureLayer");
        this.glNamedFramebufferTextureLayer = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glNamedFramebufferDrawBuffer");
        this.glNamedFramebufferDrawBuffer = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glNamedFramebufferDrawBuffers");
        this.glNamedFramebufferDrawBuffers = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glNamedFramebufferReadBuffer");
        this.glNamedFramebufferReadBuffer = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glInvalidateNamedFramebufferData");
        this.glInvalidateNamedFramebufferData = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glInvalidateNamedFramebufferSubData");
        this.glInvalidateNamedFramebufferSubData = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glClearNamedFramebufferiv");
        this.glClearNamedFramebufferiv = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glClearNamedFramebufferuiv");
        this.glClearNamedFramebufferuiv = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glClearNamedFramebufferfv");
        this.glClearNamedFramebufferfv = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glClearNamedFramebufferfi");
        this.glClearNamedFramebufferfi = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glBlitNamedFramebuffer");
        this.glBlitNamedFramebuffer = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glCheckNamedFramebufferStatus");
        this.glCheckNamedFramebufferStatus = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glGetNamedFramebufferParameteriv");
        this.glGetNamedFramebufferParameteriv = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glGetNamedFramebufferAttachmentParameteriv");
        this.glGetNamedFramebufferAttachmentParameteriv = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glCreateRenderbuffers");
        this.glCreateRenderbuffers = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glNamedRenderbufferStorage");
        this.glNamedRenderbufferStorage = functionAddress42;
        final boolean b42 = b41 & functionAddress42 != 0L;
        final long functionAddress43 = GLContext.getFunctionAddress("glNamedRenderbufferStorageMultisample");
        this.glNamedRenderbufferStorageMultisample = functionAddress43;
        final boolean b43 = b42 & functionAddress43 != 0L;
        final long functionAddress44 = GLContext.getFunctionAddress("glGetNamedRenderbufferParameteriv");
        this.glGetNamedRenderbufferParameteriv = functionAddress44;
        final boolean b44 = b43 & functionAddress44 != 0L;
        final long functionAddress45 = GLContext.getFunctionAddress("glCreateTextures");
        this.glCreateTextures = functionAddress45;
        final boolean b45 = b44 & functionAddress45 != 0L;
        final long functionAddress46 = GLContext.getFunctionAddress("glTextureBuffer");
        this.glTextureBuffer = functionAddress46;
        final boolean b46 = b45 & functionAddress46 != 0L;
        final long functionAddress47 = GLContext.getFunctionAddress("glTextureBufferRange");
        this.glTextureBufferRange = functionAddress47;
        final boolean b47 = b46 & functionAddress47 != 0L;
        final long functionAddress48 = GLContext.getFunctionAddress("glTextureStorage1D");
        this.glTextureStorage1D = functionAddress48;
        final boolean b48 = b47 & functionAddress48 != 0L;
        final long functionAddress49 = GLContext.getFunctionAddress("glTextureStorage2D");
        this.glTextureStorage2D = functionAddress49;
        final boolean b49 = b48 & functionAddress49 != 0L;
        final long functionAddress50 = GLContext.getFunctionAddress("glTextureStorage3D");
        this.glTextureStorage3D = functionAddress50;
        final boolean b50 = b49 & functionAddress50 != 0L;
        final long functionAddress51 = GLContext.getFunctionAddress("glTextureStorage2DMultisample");
        this.glTextureStorage2DMultisample = functionAddress51;
        final boolean b51 = b50 & functionAddress51 != 0L;
        final long functionAddress52 = GLContext.getFunctionAddress("glTextureStorage3DMultisample");
        this.glTextureStorage3DMultisample = functionAddress52;
        final boolean b52 = b51 & functionAddress52 != 0L;
        final long functionAddress53 = GLContext.getFunctionAddress("glTextureSubImage1D");
        this.glTextureSubImage1D = functionAddress53;
        final boolean b53 = b52 & functionAddress53 != 0L;
        final long functionAddress54 = GLContext.getFunctionAddress("glTextureSubImage2D");
        this.glTextureSubImage2D = functionAddress54;
        final boolean b54 = b53 & functionAddress54 != 0L;
        final long functionAddress55 = GLContext.getFunctionAddress("glTextureSubImage3D");
        this.glTextureSubImage3D = functionAddress55;
        final boolean b55 = b54 & functionAddress55 != 0L;
        final long functionAddress56 = GLContext.getFunctionAddress("glCompressedTextureSubImage1D");
        this.glCompressedTextureSubImage1D = functionAddress56;
        final boolean b56 = b55 & functionAddress56 != 0L;
        final long functionAddress57 = GLContext.getFunctionAddress("glCompressedTextureSubImage2D");
        this.glCompressedTextureSubImage2D = functionAddress57;
        final boolean b57 = b56 & functionAddress57 != 0L;
        final long functionAddress58 = GLContext.getFunctionAddress("glCompressedTextureSubImage3D");
        this.glCompressedTextureSubImage3D = functionAddress58;
        final boolean b58 = b57 & functionAddress58 != 0L;
        final long functionAddress59 = GLContext.getFunctionAddress("glCopyTextureSubImage1D");
        this.glCopyTextureSubImage1D = functionAddress59;
        final boolean b59 = b58 & functionAddress59 != 0L;
        final long functionAddress60 = GLContext.getFunctionAddress("glCopyTextureSubImage2D");
        this.glCopyTextureSubImage2D = functionAddress60;
        final boolean b60 = b59 & functionAddress60 != 0L;
        final long functionAddress61 = GLContext.getFunctionAddress("glCopyTextureSubImage3D");
        this.glCopyTextureSubImage3D = functionAddress61;
        final boolean b61 = b60 & functionAddress61 != 0L;
        final long functionAddress62 = GLContext.getFunctionAddress("glTextureParameterf");
        this.glTextureParameterf = functionAddress62;
        final boolean b62 = b61 & functionAddress62 != 0L;
        final long functionAddress63 = GLContext.getFunctionAddress("glTextureParameterfv");
        this.glTextureParameterfv = functionAddress63;
        final boolean b63 = b62 & functionAddress63 != 0L;
        final long functionAddress64 = GLContext.getFunctionAddress("glTextureParameteri");
        this.glTextureParameteri = functionAddress64;
        final boolean b64 = b63 & functionAddress64 != 0L;
        final long functionAddress65 = GLContext.getFunctionAddress("glTextureParameterIiv");
        this.glTextureParameterIiv = functionAddress65;
        final boolean b65 = b64 & functionAddress65 != 0L;
        final long functionAddress66 = GLContext.getFunctionAddress("glTextureParameterIuiv");
        this.glTextureParameterIuiv = functionAddress66;
        final boolean b66 = b65 & functionAddress66 != 0L;
        final long functionAddress67 = GLContext.getFunctionAddress("glTextureParameteriv");
        this.glTextureParameteriv = functionAddress67;
        final boolean b67 = b66 & functionAddress67 != 0L;
        final long functionAddress68 = GLContext.getFunctionAddress("glGenerateTextureMipmap");
        this.glGenerateTextureMipmap = functionAddress68;
        final boolean b68 = b67 & functionAddress68 != 0L;
        final long functionAddress69 = GLContext.getFunctionAddress("glBindTextureUnit");
        this.glBindTextureUnit = functionAddress69;
        final boolean b69 = b68 & functionAddress69 != 0L;
        final long functionAddress70 = GLContext.getFunctionAddress("glGetTextureImage");
        this.glGetTextureImage = functionAddress70;
        final boolean b70 = b69 & functionAddress70 != 0L;
        final long functionAddress71 = GLContext.getFunctionAddress("glGetCompressedTextureImage");
        this.glGetCompressedTextureImage = functionAddress71;
        final boolean b71 = b70 & functionAddress71 != 0L;
        final long functionAddress72 = GLContext.getFunctionAddress("glGetTextureLevelParameterfv");
        this.glGetTextureLevelParameterfv = functionAddress72;
        final boolean b72 = b71 & functionAddress72 != 0L;
        final long functionAddress73 = GLContext.getFunctionAddress("glGetTextureLevelParameteriv");
        this.glGetTextureLevelParameteriv = functionAddress73;
        final boolean b73 = b72 & functionAddress73 != 0L;
        final long functionAddress74 = GLContext.getFunctionAddress("glGetTextureParameterfv");
        this.glGetTextureParameterfv = functionAddress74;
        final boolean b74 = b73 & functionAddress74 != 0L;
        final long functionAddress75 = GLContext.getFunctionAddress("glGetTextureParameterIiv");
        this.glGetTextureParameterIiv = functionAddress75;
        final boolean b75 = b74 & functionAddress75 != 0L;
        final long functionAddress76 = GLContext.getFunctionAddress("glGetTextureParameterIuiv");
        this.glGetTextureParameterIuiv = functionAddress76;
        final boolean b76 = b75 & functionAddress76 != 0L;
        final long functionAddress77 = GLContext.getFunctionAddress("glGetTextureParameteriv");
        this.glGetTextureParameteriv = functionAddress77;
        final boolean b77 = b76 & functionAddress77 != 0L;
        final long functionAddress78 = GLContext.getFunctionAddress("glCreateVertexArrays");
        this.glCreateVertexArrays = functionAddress78;
        final boolean b78 = b77 & functionAddress78 != 0L;
        final long functionAddress79 = GLContext.getFunctionAddress("glDisableVertexArrayAttrib");
        this.glDisableVertexArrayAttrib = functionAddress79;
        final boolean b79 = b78 & functionAddress79 != 0L;
        final long functionAddress80 = GLContext.getFunctionAddress("glEnableVertexArrayAttrib");
        this.glEnableVertexArrayAttrib = functionAddress80;
        final boolean b80 = b79 & functionAddress80 != 0L;
        final long functionAddress81 = GLContext.getFunctionAddress("glVertexArrayElementBuffer");
        this.glVertexArrayElementBuffer = functionAddress81;
        final boolean b81 = b80 & functionAddress81 != 0L;
        final long functionAddress82 = GLContext.getFunctionAddress("glVertexArrayVertexBuffer");
        this.glVertexArrayVertexBuffer = functionAddress82;
        final boolean b82 = b81 & functionAddress82 != 0L;
        final long functionAddress83 = GLContext.getFunctionAddress("glVertexArrayVertexBuffers");
        this.glVertexArrayVertexBuffers = functionAddress83;
        final boolean b83 = b82 & functionAddress83 != 0L;
        final long functionAddress84 = GLContext.getFunctionAddress("glVertexArrayAttribFormat");
        this.glVertexArrayAttribFormat = functionAddress84;
        final boolean b84 = b83 & functionAddress84 != 0L;
        final long functionAddress85 = GLContext.getFunctionAddress("glVertexArrayAttribIFormat");
        this.glVertexArrayAttribIFormat = functionAddress85;
        final boolean b85 = b84 & functionAddress85 != 0L;
        final long functionAddress86 = GLContext.getFunctionAddress("glVertexArrayAttribLFormat");
        this.glVertexArrayAttribLFormat = functionAddress86;
        final boolean b86 = b85 & functionAddress86 != 0L;
        final long functionAddress87 = GLContext.getFunctionAddress("glVertexArrayAttribBinding");
        this.glVertexArrayAttribBinding = functionAddress87;
        final boolean b87 = b86 & functionAddress87 != 0L;
        final long functionAddress88 = GLContext.getFunctionAddress("glVertexArrayBindingDivisor");
        this.glVertexArrayBindingDivisor = functionAddress88;
        final boolean b88 = b87 & functionAddress88 != 0L;
        final long functionAddress89 = GLContext.getFunctionAddress("glGetVertexArrayiv");
        this.glGetVertexArrayiv = functionAddress89;
        final boolean b89 = b88 & functionAddress89 != 0L;
        final long functionAddress90 = GLContext.getFunctionAddress("glGetVertexArrayIndexediv");
        this.glGetVertexArrayIndexediv = functionAddress90;
        final boolean b90 = b89 & functionAddress90 != 0L;
        final long functionAddress91 = GLContext.getFunctionAddress("glGetVertexArrayIndexed64iv");
        this.glGetVertexArrayIndexed64iv = functionAddress91;
        final boolean b91 = b90 & functionAddress91 != 0L;
        final long functionAddress92 = GLContext.getFunctionAddress("glCreateSamplers");
        this.glCreateSamplers = functionAddress92;
        final boolean b92 = b91 & functionAddress92 != 0L;
        final long functionAddress93 = GLContext.getFunctionAddress("glCreateProgramPipelines");
        this.glCreateProgramPipelines = functionAddress93;
        final boolean b93 = b92 & functionAddress93 != 0L;
        final long functionAddress94 = GLContext.getFunctionAddress("glCreateQueries");
        this.glCreateQueries = functionAddress94;
        final boolean b94 = b93 & functionAddress94 != 0L;
        final long functionAddress95 = GLContext.getFunctionAddress("glMemoryBarrierByRegion");
        this.glMemoryBarrierByRegion = functionAddress95;
        final boolean b95 = b94 & functionAddress95 != 0L;
        final long functionAddress96 = GLContext.getFunctionAddress("glGetTextureSubImage");
        this.glGetTextureSubImage = functionAddress96;
        final boolean b96 = b95 & functionAddress96 != 0L;
        final long functionAddress97 = GLContext.getFunctionAddress("glGetCompressedTextureSubImage");
        this.glGetCompressedTextureSubImage = functionAddress97;
        final boolean b97 = b96 & functionAddress97 != 0L;
        final long functionAddress98 = GLContext.getFunctionAddress("glTextureBarrier");
        this.glTextureBarrier = functionAddress98;
        final boolean b98 = b97 & functionAddress98 != 0L;
        final long functionAddress99 = GLContext.getFunctionAddress("glGetGraphicsResetStatus");
        this.glGetGraphicsResetStatus = functionAddress99;
        final boolean b99 = b98 & functionAddress99 != 0L;
        final long functionAddress100 = GLContext.getFunctionAddress("glReadnPixels");
        this.glReadnPixels = functionAddress100;
        final boolean b100 = b99 & functionAddress100 != 0L;
        final long functionAddress101 = GLContext.getFunctionAddress("glGetnUniformfv");
        this.glGetnUniformfv = functionAddress101;
        final boolean b101 = b100 & functionAddress101 != 0L;
        final long functionAddress102 = GLContext.getFunctionAddress("glGetnUniformiv");
        this.glGetnUniformiv = functionAddress102;
        final boolean b102 = b101 & functionAddress102 != 0L;
        final long functionAddress103 = GLContext.getFunctionAddress("glGetnUniformuiv");
        this.glGetnUniformuiv = functionAddress103;
        return b102 & functionAddress103 != 0L;
    }
    
    private boolean GREMEDY_frame_terminator_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glFrameTerminatorGREMEDY");
        this.glFrameTerminatorGREMEDY = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean GREMEDY_string_marker_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glStringMarkerGREMEDY");
        this.glStringMarkerGREMEDY = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean INTEL_map_texture_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMapTexture2DINTEL");
        this.glMapTexture2DINTEL = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glUnmapTexture2DINTEL");
        this.glUnmapTexture2DINTEL = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glSyncTextureINTEL");
        this.glSyncTextureINTEL = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean KHR_debug_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDebugMessageControl");
        this.glDebugMessageControl = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDebugMessageInsert");
        this.glDebugMessageInsert = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDebugMessageCallback");
        this.glDebugMessageCallback = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetDebugMessageLog");
        this.glGetDebugMessageLog = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glPushDebugGroup");
        this.glPushDebugGroup = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glPopDebugGroup");
        this.glPopDebugGroup = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glObjectLabel");
        this.glObjectLabel = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetObjectLabel");
        this.glGetObjectLabel = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glObjectPtrLabel");
        this.glObjectPtrLabel = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glGetObjectPtrLabel");
        this.glGetObjectPtrLabel = functionAddress10;
        return b9 & functionAddress10 != 0L;
    }
    
    private boolean KHR_robustness_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetGraphicsResetStatus");
        this.glGetGraphicsResetStatus = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glReadnPixels");
        this.glReadnPixels = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetnUniformfv");
        this.glGetnUniformfv = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetnUniformiv");
        this.glGetnUniformiv = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetnUniformuiv");
        this.glGetnUniformuiv = functionAddress5;
        return b4 & functionAddress5 != 0L;
    }
    
    private boolean NV_bindless_multi_draw_indirect_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMultiDrawArraysIndirectBindlessNV");
        this.glMultiDrawArraysIndirectBindlessNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMultiDrawElementsIndirectBindlessNV");
        this.glMultiDrawElementsIndirectBindlessNV = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean NV_bindless_texture_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetTextureHandleNV");
        this.glGetTextureHandleNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetTextureSamplerHandleNV");
        this.glGetTextureSamplerHandleNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glMakeTextureHandleResidentNV");
        this.glMakeTextureHandleResidentNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glMakeTextureHandleNonResidentNV");
        this.glMakeTextureHandleNonResidentNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetImageHandleNV");
        this.glGetImageHandleNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glMakeImageHandleResidentNV");
        this.glMakeImageHandleResidentNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glMakeImageHandleNonResidentNV");
        this.glMakeImageHandleNonResidentNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glUniformHandleui64NV");
        this.glUniformHandleui64NV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glUniformHandleui64vNV");
        this.glUniformHandleui64vNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glProgramUniformHandleui64NV");
        this.glProgramUniformHandleui64NV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glProgramUniformHandleui64vNV");
        this.glProgramUniformHandleui64vNV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glIsTextureHandleResidentNV");
        this.glIsTextureHandleResidentNV = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glIsImageHandleResidentNV");
        this.glIsImageHandleResidentNV = functionAddress13;
        return b12 & functionAddress13 != 0L;
    }
    
    private boolean NV_blend_equation_advanced_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBlendParameteriNV");
        this.glBlendParameteriNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBlendBarrierNV");
        this.glBlendBarrierNV = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean NV_conditional_render_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBeginConditionalRenderNV");
        this.glBeginConditionalRenderNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glEndConditionalRenderNV");
        this.glEndConditionalRenderNV = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean NV_copy_image_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glCopyImageSubDataNV");
        this.glCopyImageSubDataNV = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean NV_depth_buffer_float_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDepthRangedNV");
        this.glDepthRangedNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glClearDepthdNV");
        this.glClearDepthdNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDepthBoundsdNV");
        this.glDepthBoundsdNV = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean NV_draw_texture_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glDrawTextureNV");
        this.glDrawTextureNV = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean NV_evaluators_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetMapControlPointsNV");
        this.glGetMapControlPointsNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMapControlPointsNV");
        this.glMapControlPointsNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glMapParameterfvNV");
        this.glMapParameterfvNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glMapParameterivNV");
        this.glMapParameterivNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetMapParameterfvNV");
        this.glGetMapParameterfvNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetMapParameterivNV");
        this.glGetMapParameterivNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetMapAttribParameterfvNV");
        this.glGetMapAttribParameterfvNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetMapAttribParameterivNV");
        this.glGetMapAttribParameterivNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glEvalMapsNV");
        this.glEvalMapsNV = functionAddress9;
        return b8 & functionAddress9 != 0L;
    }
    
    private boolean NV_explicit_multisample_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGetBooleanIndexedvEXT");
        this.glGetBooleanIndexedvEXT = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetIntegerIndexedvEXT");
        this.glGetIntegerIndexedvEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetMultisamplefvNV");
        this.glGetMultisamplefvNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glSampleMaskIndexedNV");
        this.glSampleMaskIndexedNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glTexRenderbufferNV");
        this.glTexRenderbufferNV = functionAddress5;
        return b4 & functionAddress5 != 0L;
    }
    
    private boolean NV_fence_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGenFencesNV");
        this.glGenFencesNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteFencesNV");
        this.glDeleteFencesNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glSetFenceNV");
        this.glSetFenceNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glTestFenceNV");
        this.glTestFenceNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glFinishFenceNV");
        this.glFinishFenceNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glIsFenceNV");
        this.glIsFenceNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetFenceivNV");
        this.glGetFenceivNV = functionAddress7;
        return b6 & functionAddress7 != 0L;
    }
    
    private boolean NV_fragment_program_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProgramNamedParameter4fNV");
        this.glProgramNamedParameter4fNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glProgramNamedParameter4dNV");
        this.glProgramNamedParameter4dNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetProgramNamedParameterfvNV");
        this.glGetProgramNamedParameterfvNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetProgramNamedParameterdvNV");
        this.glGetProgramNamedParameterdvNV = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean NV_framebuffer_multisample_coverage_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glRenderbufferStorageMultisampleCoverageNV");
        this.glRenderbufferStorageMultisampleCoverageNV = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean NV_geometry_program4_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProgramVertexLimitNV");
        this.glProgramVertexLimitNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glFramebufferTextureEXT");
        this.glFramebufferTextureEXT = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glFramebufferTextureLayerEXT");
        this.glFramebufferTextureLayerEXT = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glFramebufferTextureFaceEXT");
        this.glFramebufferTextureFaceEXT = functionAddress4;
        return b3 & functionAddress4 != 0L;
    }
    
    private boolean NV_gpu_program4_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProgramLocalParameterI4iNV");
        this.glProgramLocalParameterI4iNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glProgramLocalParameterI4ivNV");
        this.glProgramLocalParameterI4ivNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glProgramLocalParametersI4ivNV");
        this.glProgramLocalParametersI4ivNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glProgramLocalParameterI4uiNV");
        this.glProgramLocalParameterI4uiNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glProgramLocalParameterI4uivNV");
        this.glProgramLocalParameterI4uivNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glProgramLocalParametersI4uivNV");
        this.glProgramLocalParametersI4uivNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glProgramEnvParameterI4iNV");
        this.glProgramEnvParameterI4iNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glProgramEnvParameterI4ivNV");
        this.glProgramEnvParameterI4ivNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glProgramEnvParametersI4ivNV");
        this.glProgramEnvParametersI4ivNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glProgramEnvParameterI4uiNV");
        this.glProgramEnvParameterI4uiNV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glProgramEnvParameterI4uivNV");
        this.glProgramEnvParameterI4uivNV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glProgramEnvParametersI4uivNV");
        this.glProgramEnvParametersI4uivNV = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glGetProgramLocalParameterIivNV");
        this.glGetProgramLocalParameterIivNV = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glGetProgramLocalParameterIuivNV");
        this.glGetProgramLocalParameterIuivNV = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glGetProgramEnvParameterIivNV");
        this.glGetProgramEnvParameterIivNV = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glGetProgramEnvParameterIuivNV");
        this.glGetProgramEnvParameterIuivNV = functionAddress16;
        return b15 & functionAddress16 != 0L;
    }
    
    private boolean NV_gpu_shader5_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glUniform1i64NV");
        this.glUniform1i64NV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glUniform2i64NV");
        this.glUniform2i64NV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glUniform3i64NV");
        this.glUniform3i64NV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glUniform4i64NV");
        this.glUniform4i64NV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glUniform1i64vNV");
        this.glUniform1i64vNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glUniform2i64vNV");
        this.glUniform2i64vNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glUniform3i64vNV");
        this.glUniform3i64vNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glUniform4i64vNV");
        this.glUniform4i64vNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glUniform1ui64NV");
        this.glUniform1ui64NV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glUniform2ui64NV");
        this.glUniform2ui64NV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glUniform3ui64NV");
        this.glUniform3ui64NV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glUniform4ui64NV");
        this.glUniform4ui64NV = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glUniform1ui64vNV");
        this.glUniform1ui64vNV = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glUniform2ui64vNV");
        this.glUniform2ui64vNV = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glUniform3ui64vNV");
        this.glUniform3ui64vNV = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glUniform4ui64vNV");
        this.glUniform4ui64vNV = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glGetUniformi64vNV");
        this.glGetUniformi64vNV = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetUniformui64vNV");
        this.glGetUniformui64vNV = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        boolean b19 = false;
        Label_0428: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress19 = GLContext.getFunctionAddress("glProgramUniform1i64NV");
                this.glProgramUniform1i64NV = functionAddress19;
                if (functionAddress19 == 0L) {
                    b19 = false;
                    break Label_0428;
                }
            }
            b19 = true;
        }
        final boolean b20 = b18 & b19;
        boolean b21 = false;
        Label_0462: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress20 = GLContext.getFunctionAddress("glProgramUniform2i64NV");
                this.glProgramUniform2i64NV = functionAddress20;
                if (functionAddress20 == 0L) {
                    b21 = false;
                    break Label_0462;
                }
            }
            b21 = true;
        }
        final boolean b22 = b20 & b21;
        boolean b23 = false;
        Label_0496: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress21 = GLContext.getFunctionAddress("glProgramUniform3i64NV");
                this.glProgramUniform3i64NV = functionAddress21;
                if (functionAddress21 == 0L) {
                    b23 = false;
                    break Label_0496;
                }
            }
            b23 = true;
        }
        final boolean b24 = b22 & b23;
        boolean b25 = false;
        Label_0530: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress22 = GLContext.getFunctionAddress("glProgramUniform4i64NV");
                this.glProgramUniform4i64NV = functionAddress22;
                if (functionAddress22 == 0L) {
                    b25 = false;
                    break Label_0530;
                }
            }
            b25 = true;
        }
        final boolean b26 = b24 & b25;
        boolean b27 = false;
        Label_0564: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress23 = GLContext.getFunctionAddress("glProgramUniform1i64vNV");
                this.glProgramUniform1i64vNV = functionAddress23;
                if (functionAddress23 == 0L) {
                    b27 = false;
                    break Label_0564;
                }
            }
            b27 = true;
        }
        final boolean b28 = b26 & b27;
        boolean b29 = false;
        Label_0598: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress24 = GLContext.getFunctionAddress("glProgramUniform2i64vNV");
                this.glProgramUniform2i64vNV = functionAddress24;
                if (functionAddress24 == 0L) {
                    b29 = false;
                    break Label_0598;
                }
            }
            b29 = true;
        }
        final boolean b30 = b28 & b29;
        boolean b31 = false;
        Label_0632: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress25 = GLContext.getFunctionAddress("glProgramUniform3i64vNV");
                this.glProgramUniform3i64vNV = functionAddress25;
                if (functionAddress25 == 0L) {
                    b31 = false;
                    break Label_0632;
                }
            }
            b31 = true;
        }
        final boolean b32 = b30 & b31;
        boolean b33 = false;
        Label_0666: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress26 = GLContext.getFunctionAddress("glProgramUniform4i64vNV");
                this.glProgramUniform4i64vNV = functionAddress26;
                if (functionAddress26 == 0L) {
                    b33 = false;
                    break Label_0666;
                }
            }
            b33 = true;
        }
        final boolean b34 = b32 & b33;
        boolean b35 = false;
        Label_0700: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress27 = GLContext.getFunctionAddress("glProgramUniform1ui64NV");
                this.glProgramUniform1ui64NV = functionAddress27;
                if (functionAddress27 == 0L) {
                    b35 = false;
                    break Label_0700;
                }
            }
            b35 = true;
        }
        final boolean b36 = b34 & b35;
        boolean b37 = false;
        Label_0734: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress28 = GLContext.getFunctionAddress("glProgramUniform2ui64NV");
                this.glProgramUniform2ui64NV = functionAddress28;
                if (functionAddress28 == 0L) {
                    b37 = false;
                    break Label_0734;
                }
            }
            b37 = true;
        }
        final boolean b38 = b36 & b37;
        boolean b39 = false;
        Label_0768: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress29 = GLContext.getFunctionAddress("glProgramUniform3ui64NV");
                this.glProgramUniform3ui64NV = functionAddress29;
                if (functionAddress29 == 0L) {
                    b39 = false;
                    break Label_0768;
                }
            }
            b39 = true;
        }
        final boolean b40 = b38 & b39;
        boolean b41 = false;
        Label_0802: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress30 = GLContext.getFunctionAddress("glProgramUniform4ui64NV");
                this.glProgramUniform4ui64NV = functionAddress30;
                if (functionAddress30 == 0L) {
                    b41 = false;
                    break Label_0802;
                }
            }
            b41 = true;
        }
        final boolean b42 = b40 & b41;
        boolean b43 = false;
        Label_0836: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress31 = GLContext.getFunctionAddress("glProgramUniform1ui64vNV");
                this.glProgramUniform1ui64vNV = functionAddress31;
                if (functionAddress31 == 0L) {
                    b43 = false;
                    break Label_0836;
                }
            }
            b43 = true;
        }
        final boolean b44 = b42 & b43;
        boolean b45 = false;
        Label_0870: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress32 = GLContext.getFunctionAddress("glProgramUniform2ui64vNV");
                this.glProgramUniform2ui64vNV = functionAddress32;
                if (functionAddress32 == 0L) {
                    b45 = false;
                    break Label_0870;
                }
            }
            b45 = true;
        }
        final boolean b46 = b44 & b45;
        boolean b47 = false;
        Label_0904: {
            if (set.contains("GL_EXT_direct_state_access")) {
                final long functionAddress33 = GLContext.getFunctionAddress("glProgramUniform3ui64vNV");
                this.glProgramUniform3ui64vNV = functionAddress33;
                if (functionAddress33 == 0L) {
                    b47 = false;
                    break Label_0904;
                }
            }
            b47 = true;
        }
        final boolean b48 = b46 & b47;
        if (set.contains("GL_EXT_direct_state_access")) {
            final long functionAddress34 = GLContext.getFunctionAddress("glProgramUniform4ui64vNV");
            this.glProgramUniform4ui64vNV = functionAddress34;
            if (functionAddress34 == 0L) {
                final boolean b49 = false;
                return b48 & b49;
            }
        }
        final boolean b49 = true;
        return b48 & b49;
    }
    
    private boolean NV_half_float_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glVertex2hNV");
        this.glVertex2hNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertex3hNV");
        this.glVertex3hNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertex4hNV");
        this.glVertex4hNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glNormal3hNV");
        this.glNormal3hNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glColor3hNV");
        this.glColor3hNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glColor4hNV");
        this.glColor4hNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glTexCoord1hNV");
        this.glTexCoord1hNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glTexCoord2hNV");
        this.glTexCoord2hNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glTexCoord3hNV");
        this.glTexCoord3hNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glTexCoord4hNV");
        this.glTexCoord4hNV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glMultiTexCoord1hNV");
        this.glMultiTexCoord1hNV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glMultiTexCoord2hNV");
        this.glMultiTexCoord2hNV = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glMultiTexCoord3hNV");
        this.glMultiTexCoord3hNV = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glMultiTexCoord4hNV");
        this.glMultiTexCoord4hNV = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        boolean b15 = false;
        Label_0340: {
            if (set.contains("GL_EXT_fog_coord")) {
                final long functionAddress15 = GLContext.getFunctionAddress("glFogCoordhNV");
                this.glFogCoordhNV = functionAddress15;
                if (functionAddress15 == 0L) {
                    b15 = false;
                    break Label_0340;
                }
            }
            b15 = true;
        }
        final boolean b16 = b14 & b15;
        boolean b17 = false;
        Label_0374: {
            if (set.contains("GL_EXT_secondary_color")) {
                final long functionAddress16 = GLContext.getFunctionAddress("glSecondaryColor3hNV");
                this.glSecondaryColor3hNV = functionAddress16;
                if (functionAddress16 == 0L) {
                    b17 = false;
                    break Label_0374;
                }
            }
            b17 = true;
        }
        final boolean b18 = b16 & b17;
        boolean b19 = false;
        Label_0408: {
            if (set.contains("GL_EXT_vertex_weighting")) {
                final long functionAddress17 = GLContext.getFunctionAddress("glVertexWeighthNV");
                this.glVertexWeighthNV = functionAddress17;
                if (functionAddress17 == 0L) {
                    b19 = false;
                    break Label_0408;
                }
            }
            b19 = true;
        }
        final boolean b20 = b18 & b19;
        boolean b21 = false;
        Label_0442: {
            if (set.contains("GL_NV_vertex_program")) {
                final long functionAddress18 = GLContext.getFunctionAddress("glVertexAttrib1hNV");
                this.glVertexAttrib1hNV = functionAddress18;
                if (functionAddress18 == 0L) {
                    b21 = false;
                    break Label_0442;
                }
            }
            b21 = true;
        }
        final boolean b22 = b20 & b21;
        boolean b23 = false;
        Label_0476: {
            if (set.contains("GL_NV_vertex_program")) {
                final long functionAddress19 = GLContext.getFunctionAddress("glVertexAttrib2hNV");
                this.glVertexAttrib2hNV = functionAddress19;
                if (functionAddress19 == 0L) {
                    b23 = false;
                    break Label_0476;
                }
            }
            b23 = true;
        }
        final boolean b24 = b22 & b23;
        boolean b25 = false;
        Label_0510: {
            if (set.contains("GL_NV_vertex_program")) {
                final long functionAddress20 = GLContext.getFunctionAddress("glVertexAttrib3hNV");
                this.glVertexAttrib3hNV = functionAddress20;
                if (functionAddress20 == 0L) {
                    b25 = false;
                    break Label_0510;
                }
            }
            b25 = true;
        }
        final boolean b26 = b24 & b25;
        boolean b27 = false;
        Label_0544: {
            if (set.contains("GL_NV_vertex_program")) {
                final long functionAddress21 = GLContext.getFunctionAddress("glVertexAttrib4hNV");
                this.glVertexAttrib4hNV = functionAddress21;
                if (functionAddress21 == 0L) {
                    b27 = false;
                    break Label_0544;
                }
            }
            b27 = true;
        }
        final boolean b28 = b26 & b27;
        boolean b29 = false;
        Label_0578: {
            if (set.contains("GL_NV_vertex_program")) {
                final long functionAddress22 = GLContext.getFunctionAddress("glVertexAttribs1hvNV");
                this.glVertexAttribs1hvNV = functionAddress22;
                if (functionAddress22 == 0L) {
                    b29 = false;
                    break Label_0578;
                }
            }
            b29 = true;
        }
        final boolean b30 = b28 & b29;
        boolean b31 = false;
        Label_0612: {
            if (set.contains("GL_NV_vertex_program")) {
                final long functionAddress23 = GLContext.getFunctionAddress("glVertexAttribs2hvNV");
                this.glVertexAttribs2hvNV = functionAddress23;
                if (functionAddress23 == 0L) {
                    b31 = false;
                    break Label_0612;
                }
            }
            b31 = true;
        }
        final boolean b32 = b30 & b31;
        boolean b33 = false;
        Label_0646: {
            if (set.contains("GL_NV_vertex_program")) {
                final long functionAddress24 = GLContext.getFunctionAddress("glVertexAttribs3hvNV");
                this.glVertexAttribs3hvNV = functionAddress24;
                if (functionAddress24 == 0L) {
                    b33 = false;
                    break Label_0646;
                }
            }
            b33 = true;
        }
        final boolean b34 = b32 & b33;
        if (set.contains("GL_NV_vertex_program")) {
            final long functionAddress25 = GLContext.getFunctionAddress("glVertexAttribs4hvNV");
            this.glVertexAttribs4hvNV = functionAddress25;
            if (functionAddress25 == 0L) {
                final boolean b35 = false;
                return b34 & b35;
            }
        }
        final boolean b35 = true;
        return b34 & b35;
    }
    
    private boolean NV_occlusion_query_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glGenOcclusionQueriesNV");
        this.glGenOcclusionQueriesNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteOcclusionQueriesNV");
        this.glDeleteOcclusionQueriesNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glIsOcclusionQueryNV");
        this.glIsOcclusionQueryNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glBeginOcclusionQueryNV");
        this.glBeginOcclusionQueryNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glEndOcclusionQueryNV");
        this.glEndOcclusionQueryNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetOcclusionQueryuivNV");
        this.glGetOcclusionQueryuivNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetOcclusionQueryivNV");
        this.glGetOcclusionQueryivNV = functionAddress7;
        return b6 & functionAddress7 != 0L;
    }
    
    private boolean NV_parameter_buffer_object_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glProgramBufferParametersfvNV");
        this.glProgramBufferParametersfvNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glProgramBufferParametersIivNV");
        this.glProgramBufferParametersIivNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glProgramBufferParametersIuivNV");
        this.glProgramBufferParametersIuivNV = functionAddress3;
        return b2 & functionAddress3 != 0L;
    }
    
    private boolean NV_path_rendering_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glPathCommandsNV");
        this.glPathCommandsNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glPathCoordsNV");
        this.glPathCoordsNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glPathSubCommandsNV");
        this.glPathSubCommandsNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glPathSubCoordsNV");
        this.glPathSubCoordsNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glPathStringNV");
        this.glPathStringNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glPathGlyphsNV");
        this.glPathGlyphsNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glPathGlyphRangeNV");
        this.glPathGlyphRangeNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glWeightPathsNV");
        this.glWeightPathsNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glCopyPathNV");
        this.glCopyPathNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glInterpolatePathsNV");
        this.glInterpolatePathsNV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glTransformPathNV");
        this.glTransformPathNV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glPathParameterivNV");
        this.glPathParameterivNV = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glPathParameteriNV");
        this.glPathParameteriNV = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glPathParameterfvNV");
        this.glPathParameterfvNV = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glPathParameterfNV");
        this.glPathParameterfNV = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glPathDashArrayNV");
        this.glPathDashArrayNV = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glGenPathsNV");
        this.glGenPathsNV = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glDeletePathsNV");
        this.glDeletePathsNV = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glIsPathNV");
        this.glIsPathNV = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glPathStencilFuncNV");
        this.glPathStencilFuncNV = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glPathStencilDepthOffsetNV");
        this.glPathStencilDepthOffsetNV = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glStencilFillPathNV");
        this.glStencilFillPathNV = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glStencilStrokePathNV");
        this.glStencilStrokePathNV = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glStencilFillPathInstancedNV");
        this.glStencilFillPathInstancedNV = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glStencilStrokePathInstancedNV");
        this.glStencilStrokePathInstancedNV = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glPathCoverDepthFuncNV");
        this.glPathCoverDepthFuncNV = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glPathColorGenNV");
        this.glPathColorGenNV = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glPathTexGenNV");
        this.glPathTexGenNV = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glPathFogGenNV");
        this.glPathFogGenNV = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glCoverFillPathNV");
        this.glCoverFillPathNV = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glCoverStrokePathNV");
        this.glCoverStrokePathNV = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glCoverFillPathInstancedNV");
        this.glCoverFillPathInstancedNV = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glCoverStrokePathInstancedNV");
        this.glCoverStrokePathInstancedNV = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glGetPathParameterivNV");
        this.glGetPathParameterivNV = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glGetPathParameterfvNV");
        this.glGetPathParameterfvNV = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glGetPathCommandsNV");
        this.glGetPathCommandsNV = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glGetPathCoordsNV");
        this.glGetPathCoordsNV = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glGetPathDashArrayNV");
        this.glGetPathDashArrayNV = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glGetPathMetricsNV");
        this.glGetPathMetricsNV = functionAddress39;
        final boolean b39 = b38 & functionAddress39 != 0L;
        final long functionAddress40 = GLContext.getFunctionAddress("glGetPathMetricRangeNV");
        this.glGetPathMetricRangeNV = functionAddress40;
        final boolean b40 = b39 & functionAddress40 != 0L;
        final long functionAddress41 = GLContext.getFunctionAddress("glGetPathSpacingNV");
        this.glGetPathSpacingNV = functionAddress41;
        final boolean b41 = b40 & functionAddress41 != 0L;
        final long functionAddress42 = GLContext.getFunctionAddress("glGetPathColorGenivNV");
        this.glGetPathColorGenivNV = functionAddress42;
        final boolean b42 = b41 & functionAddress42 != 0L;
        final long functionAddress43 = GLContext.getFunctionAddress("glGetPathColorGenfvNV");
        this.glGetPathColorGenfvNV = functionAddress43;
        final boolean b43 = b42 & functionAddress43 != 0L;
        final long functionAddress44 = GLContext.getFunctionAddress("glGetPathTexGenivNV");
        this.glGetPathTexGenivNV = functionAddress44;
        final boolean b44 = b43 & functionAddress44 != 0L;
        final long functionAddress45 = GLContext.getFunctionAddress("glGetPathTexGenfvNV");
        this.glGetPathTexGenfvNV = functionAddress45;
        final boolean b45 = b44 & functionAddress45 != 0L;
        final long functionAddress46 = GLContext.getFunctionAddress("glIsPointInFillPathNV");
        this.glIsPointInFillPathNV = functionAddress46;
        final boolean b46 = b45 & functionAddress46 != 0L;
        final long functionAddress47 = GLContext.getFunctionAddress("glIsPointInStrokePathNV");
        this.glIsPointInStrokePathNV = functionAddress47;
        final boolean b47 = b46 & functionAddress47 != 0L;
        final long functionAddress48 = GLContext.getFunctionAddress("glGetPathLengthNV");
        this.glGetPathLengthNV = functionAddress48;
        final boolean b48 = b47 & functionAddress48 != 0L;
        final long functionAddress49 = GLContext.getFunctionAddress("glPointAlongPathNV");
        this.glPointAlongPathNV = functionAddress49;
        return b48 & functionAddress49 != 0L;
    }
    
    private boolean NV_pixel_data_range_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glPixelDataRangeNV");
        this.glPixelDataRangeNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glFlushPixelDataRangeNV");
        this.glFlushPixelDataRangeNV = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean NV_point_sprite_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glPointParameteriNV");
        this.glPointParameteriNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glPointParameterivNV");
        this.glPointParameterivNV = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean NV_present_video_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glPresentFrameKeyedNV");
        this.glPresentFrameKeyedNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glPresentFrameDualFillNV");
        this.glPresentFrameDualFillNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetVideoivNV");
        this.glGetVideoivNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetVideouivNV");
        this.glGetVideouivNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetVideoi64vNV");
        this.glGetVideoi64vNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetVideoui64vNV");
        this.glGetVideoui64vNV = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean NV_primitive_restart_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glPrimitiveRestartNV");
        this.glPrimitiveRestartNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glPrimitiveRestartIndexNV");
        this.glPrimitiveRestartIndexNV = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean NV_program_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glLoadProgramNV");
        this.glLoadProgramNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBindProgramNV");
        this.glBindProgramNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glDeleteProgramsNV");
        this.glDeleteProgramsNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGenProgramsNV");
        this.glGenProgramsNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetProgramivNV");
        this.glGetProgramivNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetProgramStringNV");
        this.glGetProgramStringNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glIsProgramNV");
        this.glIsProgramNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glAreProgramsResidentNV");
        this.glAreProgramsResidentNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glRequestResidentProgramsNV");
        this.glRequestResidentProgramsNV = functionAddress9;
        return b8 & functionAddress9 != 0L;
    }
    
    private boolean NV_register_combiners_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glCombinerParameterfNV");
        this.glCombinerParameterfNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glCombinerParameterfvNV");
        this.glCombinerParameterfvNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glCombinerParameteriNV");
        this.glCombinerParameteriNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glCombinerParameterivNV");
        this.glCombinerParameterivNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glCombinerInputNV");
        this.glCombinerInputNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glCombinerOutputNV");
        this.glCombinerOutputNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glFinalCombinerInputNV");
        this.glFinalCombinerInputNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetCombinerInputParameterfvNV");
        this.glGetCombinerInputParameterfvNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetCombinerInputParameterivNV");
        this.glGetCombinerInputParameterivNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glGetCombinerOutputParameterfvNV");
        this.glGetCombinerOutputParameterfvNV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGetCombinerOutputParameterivNV");
        this.glGetCombinerOutputParameterivNV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glGetFinalCombinerInputParameterfvNV");
        this.glGetFinalCombinerInputParameterfvNV = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glGetFinalCombinerInputParameterivNV");
        this.glGetFinalCombinerInputParameterivNV = functionAddress13;
        return b12 & functionAddress13 != 0L;
    }
    
    private boolean NV_register_combiners2_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glCombinerStageParameterfvNV");
        this.glCombinerStageParameterfvNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetCombinerStageParameterfvNV");
        this.glGetCombinerStageParameterfvNV = functionAddress2;
        return b & functionAddress2 != 0L;
    }
    
    private boolean NV_shader_buffer_load_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glMakeBufferResidentNV");
        this.glMakeBufferResidentNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glMakeBufferNonResidentNV");
        this.glMakeBufferNonResidentNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glIsBufferResidentNV");
        this.glIsBufferResidentNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glMakeNamedBufferResidentNV");
        this.glMakeNamedBufferResidentNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glMakeNamedBufferNonResidentNV");
        this.glMakeNamedBufferNonResidentNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glIsNamedBufferResidentNV");
        this.glIsNamedBufferResidentNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetBufferParameterui64vNV");
        this.glGetBufferParameterui64vNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetNamedBufferParameterui64vNV");
        this.glGetNamedBufferParameterui64vNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetIntegerui64vNV");
        this.glGetIntegerui64vNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glUniformui64NV");
        this.glUniformui64NV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glUniformui64vNV");
        this.glUniformui64vNV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glGetUniformui64vNV");
        this.glGetUniformui64vNV = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glProgramUniformui64NV");
        this.glProgramUniformui64NV = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glProgramUniformui64vNV");
        this.glProgramUniformui64vNV = functionAddress14;
        return b13 & functionAddress14 != 0L;
    }
    
    private boolean NV_texture_barrier_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTextureBarrierNV");
        this.glTextureBarrierNV = functionAddress;
        return functionAddress != 0L;
    }
    
    private boolean NV_texture_multisample_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glTexImage2DMultisampleCoverageNV");
        this.glTexImage2DMultisampleCoverageNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glTexImage3DMultisampleCoverageNV");
        this.glTexImage3DMultisampleCoverageNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glTextureImage2DMultisampleNV");
        this.glTextureImage2DMultisampleNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glTextureImage3DMultisampleNV");
        this.glTextureImage3DMultisampleNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glTextureImage2DMultisampleCoverageNV");
        this.glTextureImage2DMultisampleCoverageNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glTextureImage3DMultisampleCoverageNV");
        this.glTextureImage3DMultisampleCoverageNV = functionAddress6;
        return b5 & functionAddress6 != 0L;
    }
    
    private boolean NV_transform_feedback_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindBufferRangeNV");
        this.glBindBufferRangeNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBindBufferOffsetNV");
        this.glBindBufferOffsetNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glBindBufferBaseNV");
        this.glBindBufferBaseNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glTransformFeedbackAttribsNV");
        this.glTransformFeedbackAttribsNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glTransformFeedbackVaryingsNV");
        this.glTransformFeedbackVaryingsNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glBeginTransformFeedbackNV");
        this.glBeginTransformFeedbackNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glEndTransformFeedbackNV");
        this.glEndTransformFeedbackNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetVaryingLocationNV");
        this.glGetVaryingLocationNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glGetActiveVaryingNV");
        this.glGetActiveVaryingNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glActiveVaryingNV");
        this.glActiveVaryingNV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glGetTransformFeedbackVaryingNV");
        this.glGetTransformFeedbackVaryingNV = functionAddress11;
        return b10 & functionAddress11 != 0L;
    }
    
    private boolean NV_transform_feedback2_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBindTransformFeedbackNV");
        this.glBindTransformFeedbackNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glDeleteTransformFeedbacksNV");
        this.glDeleteTransformFeedbacksNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGenTransformFeedbacksNV");
        this.glGenTransformFeedbacksNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glIsTransformFeedbackNV");
        this.glIsTransformFeedbackNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glPauseTransformFeedbackNV");
        this.glPauseTransformFeedbackNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glResumeTransformFeedbackNV");
        this.glResumeTransformFeedbackNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glDrawTransformFeedbackNV");
        this.glDrawTransformFeedbackNV = functionAddress7;
        return b6 & functionAddress7 != 0L;
    }
    
    private boolean NV_vertex_array_range_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glVertexArrayRangeNV");
        this.glVertexArrayRangeNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glFlushVertexArrayRangeNV");
        this.glFlushVertexArrayRangeNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long platformSpecificFunctionAddress = GLContext.getPlatformSpecificFunctionAddress("gl", new String[] { "Windows", "Linux" }, new String[] { "wgl", "glX" }, "glAllocateMemoryNV");
        this.glAllocateMemoryNV = platformSpecificFunctionAddress;
        final boolean b3 = b2 & platformSpecificFunctionAddress != 0L;
        final long platformSpecificFunctionAddress2 = GLContext.getPlatformSpecificFunctionAddress("gl", new String[] { "Windows", "Linux" }, new String[] { "wgl", "glX" }, "glFreeMemoryNV");
        this.glFreeMemoryNV = platformSpecificFunctionAddress2;
        return b3 & platformSpecificFunctionAddress2 != 0L;
    }
    
    private boolean NV_vertex_attrib_integer_64bit_initNativeFunctionAddresses(final Set set) {
        final long functionAddress = GLContext.getFunctionAddress("glVertexAttribL1i64NV");
        this.glVertexAttribL1i64NV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexAttribL2i64NV");
        this.glVertexAttribL2i64NV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glVertexAttribL3i64NV");
        this.glVertexAttribL3i64NV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glVertexAttribL4i64NV");
        this.glVertexAttribL4i64NV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glVertexAttribL1i64vNV");
        this.glVertexAttribL1i64vNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glVertexAttribL2i64vNV");
        this.glVertexAttribL2i64vNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glVertexAttribL3i64vNV");
        this.glVertexAttribL3i64vNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glVertexAttribL4i64vNV");
        this.glVertexAttribL4i64vNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glVertexAttribL1ui64NV");
        this.glVertexAttribL1ui64NV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVertexAttribL2ui64NV");
        this.glVertexAttribL2ui64NV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glVertexAttribL3ui64NV");
        this.glVertexAttribL3ui64NV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glVertexAttribL4ui64NV");
        this.glVertexAttribL4ui64NV = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glVertexAttribL1ui64vNV");
        this.glVertexAttribL1ui64vNV = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glVertexAttribL2ui64vNV");
        this.glVertexAttribL2ui64vNV = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glVertexAttribL3ui64vNV");
        this.glVertexAttribL3ui64vNV = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glVertexAttribL4ui64vNV");
        this.glVertexAttribL4ui64vNV = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glGetVertexAttribLi64vNV");
        this.glGetVertexAttribLi64vNV = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glGetVertexAttribLui64vNV");
        this.glGetVertexAttribLui64vNV = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        if (set.contains("GL_NV_vertex_buffer_unified_memory")) {
            final long functionAddress19 = GLContext.getFunctionAddress("glVertexAttribLFormatNV");
            this.glVertexAttribLFormatNV = functionAddress19;
            if (functionAddress19 == 0L) {
                final boolean b19 = false;
                return b18 & b19;
            }
        }
        final boolean b19 = true;
        return b18 & b19;
    }
    
    private boolean NV_vertex_buffer_unified_memory_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBufferAddressRangeNV");
        this.glBufferAddressRangeNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glVertexFormatNV");
        this.glVertexFormatNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glNormalFormatNV");
        this.glNormalFormatNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glColorFormatNV");
        this.glColorFormatNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glIndexFormatNV");
        this.glIndexFormatNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glTexCoordFormatNV");
        this.glTexCoordFormatNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glEdgeFlagFormatNV");
        this.glEdgeFlagFormatNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glSecondaryColorFormatNV");
        this.glSecondaryColorFormatNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glFogCoordFormatNV");
        this.glFogCoordFormatNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVertexAttribFormatNV");
        this.glVertexAttribFormatNV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glVertexAttribIFormatNV");
        this.glVertexAttribIFormatNV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glGetIntegerui64i_vNV");
        this.glGetIntegerui64i_vNV = functionAddress12;
        return b11 & functionAddress12 != 0L;
    }
    
    private boolean NV_vertex_program_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glExecuteProgramNV");
        this.glExecuteProgramNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glGetProgramParameterfvNV");
        this.glGetProgramParameterfvNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glGetProgramParameterdvNV");
        this.glGetProgramParameterdvNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glGetTrackMatrixivNV");
        this.glGetTrackMatrixivNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetVertexAttribfvNV");
        this.glGetVertexAttribfvNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetVertexAttribdvNV");
        this.glGetVertexAttribdvNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetVertexAttribivNV");
        this.glGetVertexAttribivNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetVertexAttribPointervNV");
        this.glGetVertexAttribPointervNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glProgramParameter4fNV");
        this.glProgramParameter4fNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glProgramParameter4dNV");
        this.glProgramParameter4dNV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glProgramParameters4fvNV");
        this.glProgramParameters4fvNV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glProgramParameters4dvNV");
        this.glProgramParameters4dvNV = functionAddress12;
        final boolean b12 = b11 & functionAddress12 != 0L;
        final long functionAddress13 = GLContext.getFunctionAddress("glTrackMatrixNV");
        this.glTrackMatrixNV = functionAddress13;
        final boolean b13 = b12 & functionAddress13 != 0L;
        final long functionAddress14 = GLContext.getFunctionAddress("glVertexAttribPointerNV");
        this.glVertexAttribPointerNV = functionAddress14;
        final boolean b14 = b13 & functionAddress14 != 0L;
        final long functionAddress15 = GLContext.getFunctionAddress("glVertexAttrib1sNV");
        this.glVertexAttrib1sNV = functionAddress15;
        final boolean b15 = b14 & functionAddress15 != 0L;
        final long functionAddress16 = GLContext.getFunctionAddress("glVertexAttrib1fNV");
        this.glVertexAttrib1fNV = functionAddress16;
        final boolean b16 = b15 & functionAddress16 != 0L;
        final long functionAddress17 = GLContext.getFunctionAddress("glVertexAttrib1dNV");
        this.glVertexAttrib1dNV = functionAddress17;
        final boolean b17 = b16 & functionAddress17 != 0L;
        final long functionAddress18 = GLContext.getFunctionAddress("glVertexAttrib2sNV");
        this.glVertexAttrib2sNV = functionAddress18;
        final boolean b18 = b17 & functionAddress18 != 0L;
        final long functionAddress19 = GLContext.getFunctionAddress("glVertexAttrib2fNV");
        this.glVertexAttrib2fNV = functionAddress19;
        final boolean b19 = b18 & functionAddress19 != 0L;
        final long functionAddress20 = GLContext.getFunctionAddress("glVertexAttrib2dNV");
        this.glVertexAttrib2dNV = functionAddress20;
        final boolean b20 = b19 & functionAddress20 != 0L;
        final long functionAddress21 = GLContext.getFunctionAddress("glVertexAttrib3sNV");
        this.glVertexAttrib3sNV = functionAddress21;
        final boolean b21 = b20 & functionAddress21 != 0L;
        final long functionAddress22 = GLContext.getFunctionAddress("glVertexAttrib3fNV");
        this.glVertexAttrib3fNV = functionAddress22;
        final boolean b22 = b21 & functionAddress22 != 0L;
        final long functionAddress23 = GLContext.getFunctionAddress("glVertexAttrib3dNV");
        this.glVertexAttrib3dNV = functionAddress23;
        final boolean b23 = b22 & functionAddress23 != 0L;
        final long functionAddress24 = GLContext.getFunctionAddress("glVertexAttrib4sNV");
        this.glVertexAttrib4sNV = functionAddress24;
        final boolean b24 = b23 & functionAddress24 != 0L;
        final long functionAddress25 = GLContext.getFunctionAddress("glVertexAttrib4fNV");
        this.glVertexAttrib4fNV = functionAddress25;
        final boolean b25 = b24 & functionAddress25 != 0L;
        final long functionAddress26 = GLContext.getFunctionAddress("glVertexAttrib4dNV");
        this.glVertexAttrib4dNV = functionAddress26;
        final boolean b26 = b25 & functionAddress26 != 0L;
        final long functionAddress27 = GLContext.getFunctionAddress("glVertexAttrib4ubNV");
        this.glVertexAttrib4ubNV = functionAddress27;
        final boolean b27 = b26 & functionAddress27 != 0L;
        final long functionAddress28 = GLContext.getFunctionAddress("glVertexAttribs1svNV");
        this.glVertexAttribs1svNV = functionAddress28;
        final boolean b28 = b27 & functionAddress28 != 0L;
        final long functionAddress29 = GLContext.getFunctionAddress("glVertexAttribs1fvNV");
        this.glVertexAttribs1fvNV = functionAddress29;
        final boolean b29 = b28 & functionAddress29 != 0L;
        final long functionAddress30 = GLContext.getFunctionAddress("glVertexAttribs1dvNV");
        this.glVertexAttribs1dvNV = functionAddress30;
        final boolean b30 = b29 & functionAddress30 != 0L;
        final long functionAddress31 = GLContext.getFunctionAddress("glVertexAttribs2svNV");
        this.glVertexAttribs2svNV = functionAddress31;
        final boolean b31 = b30 & functionAddress31 != 0L;
        final long functionAddress32 = GLContext.getFunctionAddress("glVertexAttribs2fvNV");
        this.glVertexAttribs2fvNV = functionAddress32;
        final boolean b32 = b31 & functionAddress32 != 0L;
        final long functionAddress33 = GLContext.getFunctionAddress("glVertexAttribs2dvNV");
        this.glVertexAttribs2dvNV = functionAddress33;
        final boolean b33 = b32 & functionAddress33 != 0L;
        final long functionAddress34 = GLContext.getFunctionAddress("glVertexAttribs3svNV");
        this.glVertexAttribs3svNV = functionAddress34;
        final boolean b34 = b33 & functionAddress34 != 0L;
        final long functionAddress35 = GLContext.getFunctionAddress("glVertexAttribs3fvNV");
        this.glVertexAttribs3fvNV = functionAddress35;
        final boolean b35 = b34 & functionAddress35 != 0L;
        final long functionAddress36 = GLContext.getFunctionAddress("glVertexAttribs3dvNV");
        this.glVertexAttribs3dvNV = functionAddress36;
        final boolean b36 = b35 & functionAddress36 != 0L;
        final long functionAddress37 = GLContext.getFunctionAddress("glVertexAttribs4svNV");
        this.glVertexAttribs4svNV = functionAddress37;
        final boolean b37 = b36 & functionAddress37 != 0L;
        final long functionAddress38 = GLContext.getFunctionAddress("glVertexAttribs4fvNV");
        this.glVertexAttribs4fvNV = functionAddress38;
        final boolean b38 = b37 & functionAddress38 != 0L;
        final long functionAddress39 = GLContext.getFunctionAddress("glVertexAttribs4dvNV");
        this.glVertexAttribs4dvNV = functionAddress39;
        return b38 & functionAddress39 != 0L;
    }
    
    private boolean NV_video_capture_initNativeFunctionAddresses() {
        final long functionAddress = GLContext.getFunctionAddress("glBeginVideoCaptureNV");
        this.glBeginVideoCaptureNV = functionAddress;
        final boolean b = functionAddress != 0L;
        final long functionAddress2 = GLContext.getFunctionAddress("glBindVideoCaptureStreamBufferNV");
        this.glBindVideoCaptureStreamBufferNV = functionAddress2;
        final boolean b2 = b & functionAddress2 != 0L;
        final long functionAddress3 = GLContext.getFunctionAddress("glBindVideoCaptureStreamTextureNV");
        this.glBindVideoCaptureStreamTextureNV = functionAddress3;
        final boolean b3 = b2 & functionAddress3 != 0L;
        final long functionAddress4 = GLContext.getFunctionAddress("glEndVideoCaptureNV");
        this.glEndVideoCaptureNV = functionAddress4;
        final boolean b4 = b3 & functionAddress4 != 0L;
        final long functionAddress5 = GLContext.getFunctionAddress("glGetVideoCaptureivNV");
        this.glGetVideoCaptureivNV = functionAddress5;
        final boolean b5 = b4 & functionAddress5 != 0L;
        final long functionAddress6 = GLContext.getFunctionAddress("glGetVideoCaptureStreamivNV");
        this.glGetVideoCaptureStreamivNV = functionAddress6;
        final boolean b6 = b5 & functionAddress6 != 0L;
        final long functionAddress7 = GLContext.getFunctionAddress("glGetVideoCaptureStreamfvNV");
        this.glGetVideoCaptureStreamfvNV = functionAddress7;
        final boolean b7 = b6 & functionAddress7 != 0L;
        final long functionAddress8 = GLContext.getFunctionAddress("glGetVideoCaptureStreamdvNV");
        this.glGetVideoCaptureStreamdvNV = functionAddress8;
        final boolean b8 = b7 & functionAddress8 != 0L;
        final long functionAddress9 = GLContext.getFunctionAddress("glVideoCaptureNV");
        this.glVideoCaptureNV = functionAddress9;
        final boolean b9 = b8 & functionAddress9 != 0L;
        final long functionAddress10 = GLContext.getFunctionAddress("glVideoCaptureStreamParameterivNV");
        this.glVideoCaptureStreamParameterivNV = functionAddress10;
        final boolean b10 = b9 & functionAddress10 != 0L;
        final long functionAddress11 = GLContext.getFunctionAddress("glVideoCaptureStreamParameterfvNV");
        this.glVideoCaptureStreamParameterfvNV = functionAddress11;
        final boolean b11 = b10 & functionAddress11 != 0L;
        final long functionAddress12 = GLContext.getFunctionAddress("glVideoCaptureStreamParameterdvNV");
        this.glVideoCaptureStreamParameterdvNV = functionAddress12;
        return b11 & functionAddress12 != 0L;
    }
    
    private static void remove(final Set set, final String s) {
        LWJGLUtil.log(s + " was reported as available but an entry point is missing");
        set.remove(s);
    }
    
    private Set initAllStubs(final boolean b) throws LWJGLException {
        this.glGetError = GLContext.getFunctionAddress("glGetError");
        this.glGetString = GLContext.getFunctionAddress("glGetString");
        this.glGetIntegerv = GLContext.getFunctionAddress("glGetIntegerv");
        this.glGetStringi = GLContext.getFunctionAddress("glGetStringi");
        GLContext.setCapabilities(this);
        final HashSet<String> set = new HashSet<String>(256);
        final int supportedExtensions = GLContext.getSupportedExtensions(set);
        if (!set.contains("OpenGL31") || set.contains("GL_ARB_compatibility") || (supportedExtensions & 0x2) == 0x0) {}
        if (!this.GL11_initNativeFunctionAddresses(true)) {
            throw new LWJGLException("GL11 not supported");
        }
        if (set.contains("GL_ARB_fragment_program")) {
            set.add("GL_ARB_program");
        }
        if (set.contains("GL_ARB_pixel_buffer_object")) {
            set.add("GL_ARB_buffer_object");
        }
        if (set.contains("GL_ARB_vertex_buffer_object")) {
            set.add("GL_ARB_buffer_object");
        }
        if (set.contains("GL_ARB_vertex_program")) {
            set.add("GL_ARB_program");
        }
        if (set.contains("GL_EXT_pixel_buffer_object")) {
            set.add("GL_ARB_buffer_object");
        }
        if (set.contains("GL_NV_fragment_program")) {
            set.add("GL_NV_program");
        }
        if (set.contains("GL_NV_vertex_program")) {
            set.add("GL_NV_program");
        }
        if ((set.contains("GL_AMD_debug_output") || set.contains("GL_AMDX_debug_output")) && !this.AMD_debug_output_initNativeFunctionAddresses()) {
            remove(set, "GL_AMDX_debug_output");
            remove(set, "GL_AMD_debug_output");
        }
        if (set.contains("GL_AMD_draw_buffers_blend") && !this.AMD_draw_buffers_blend_initNativeFunctionAddresses()) {
            remove(set, "GL_AMD_draw_buffers_blend");
        }
        if (set.contains("GL_AMD_interleaved_elements") && !this.AMD_interleaved_elements_initNativeFunctionAddresses()) {
            remove(set, "GL_AMD_interleaved_elements");
        }
        if (set.contains("GL_AMD_multi_draw_indirect") && !this.AMD_multi_draw_indirect_initNativeFunctionAddresses()) {
            remove(set, "GL_AMD_multi_draw_indirect");
        }
        if (set.contains("GL_AMD_name_gen_delete") && !this.AMD_name_gen_delete_initNativeFunctionAddresses()) {
            remove(set, "GL_AMD_name_gen_delete");
        }
        if (set.contains("GL_AMD_performance_monitor") && !this.AMD_performance_monitor_initNativeFunctionAddresses()) {
            remove(set, "GL_AMD_performance_monitor");
        }
        if (set.contains("GL_AMD_sample_positions") && !this.AMD_sample_positions_initNativeFunctionAddresses()) {
            remove(set, "GL_AMD_sample_positions");
        }
        if (set.contains("GL_AMD_sparse_texture") && !this.AMD_sparse_texture_initNativeFunctionAddresses()) {
            remove(set, "GL_AMD_sparse_texture");
        }
        if (set.contains("GL_AMD_stencil_operation_extended") && !this.AMD_stencil_operation_extended_initNativeFunctionAddresses()) {
            remove(set, "GL_AMD_stencil_operation_extended");
        }
        if (set.contains("GL_AMD_vertex_shader_tessellator") && !this.AMD_vertex_shader_tessellator_initNativeFunctionAddresses()) {
            remove(set, "GL_AMD_vertex_shader_tessellator");
        }
        if (set.contains("GL_APPLE_element_array") && !this.APPLE_element_array_initNativeFunctionAddresses()) {
            remove(set, "GL_APPLE_element_array");
        }
        if (set.contains("GL_APPLE_fence") && !this.APPLE_fence_initNativeFunctionAddresses()) {
            remove(set, "GL_APPLE_fence");
        }
        if (set.contains("GL_APPLE_flush_buffer_range") && !this.APPLE_flush_buffer_range_initNativeFunctionAddresses()) {
            remove(set, "GL_APPLE_flush_buffer_range");
        }
        if (set.contains("GL_APPLE_object_purgeable") && !this.APPLE_object_purgeable_initNativeFunctionAddresses()) {
            remove(set, "GL_APPLE_object_purgeable");
        }
        if (set.contains("GL_APPLE_texture_range") && !this.APPLE_texture_range_initNativeFunctionAddresses()) {
            remove(set, "GL_APPLE_texture_range");
        }
        if (set.contains("GL_APPLE_vertex_array_object") && !this.APPLE_vertex_array_object_initNativeFunctionAddresses()) {
            remove(set, "GL_APPLE_vertex_array_object");
        }
        if (set.contains("GL_APPLE_vertex_array_range") && !this.APPLE_vertex_array_range_initNativeFunctionAddresses()) {
            remove(set, "GL_APPLE_vertex_array_range");
        }
        if (set.contains("GL_APPLE_vertex_program_evaluators") && !this.APPLE_vertex_program_evaluators_initNativeFunctionAddresses()) {
            remove(set, "GL_APPLE_vertex_program_evaluators");
        }
        if (set.contains("GL_ARB_ES2_compatibility") && !this.ARB_ES2_compatibility_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_ES2_compatibility");
        }
        if (set.contains("GL_ARB_ES3_1_compatibility") && !this.ARB_ES3_1_compatibility_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_ES3_1_compatibility");
        }
        if (set.contains("GL_ARB_base_instance") && !this.ARB_base_instance_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_base_instance");
        }
        if (set.contains("GL_ARB_bindless_texture") && !this.ARB_bindless_texture_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_bindless_texture");
        }
        if (set.contains("GL_ARB_blend_func_extended") && !this.ARB_blend_func_extended_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_blend_func_extended");
        }
        if (set.contains("GL_ARB_buffer_object") && !this.ARB_buffer_object_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_buffer_object");
        }
        if (set.contains("GL_ARB_buffer_storage") && !this.ARB_buffer_storage_initNativeFunctionAddresses(set)) {
            remove(set, "GL_ARB_buffer_storage");
        }
        if (set.contains("GL_ARB_cl_event") && !this.ARB_cl_event_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_cl_event");
        }
        if (set.contains("GL_ARB_clear_buffer_object") && !this.ARB_clear_buffer_object_initNativeFunctionAddresses(set)) {
            remove(set, "GL_ARB_clear_buffer_object");
        }
        if (set.contains("GL_ARB_clear_texture") && !this.ARB_clear_texture_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_clear_texture");
        }
        if (set.contains("GL_ARB_clip_control") && !this.ARB_clip_control_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_clip_control");
        }
        if (set.contains("GL_ARB_color_buffer_float") && !this.ARB_color_buffer_float_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_color_buffer_float");
        }
        if (set.contains("GL_ARB_compute_shader") && !this.ARB_compute_shader_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_compute_shader");
        }
        if (set.contains("GL_ARB_compute_variable_group_size") && !this.ARB_compute_variable_group_size_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_compute_variable_group_size");
        }
        if (set.contains("GL_ARB_copy_buffer") && !this.ARB_copy_buffer_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_copy_buffer");
        }
        if (set.contains("GL_ARB_copy_image") && !this.ARB_copy_image_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_copy_image");
        }
        if (set.contains("GL_ARB_debug_output") && !this.ARB_debug_output_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_debug_output");
        }
        if (set.contains("GL_ARB_direct_state_access") && !this.ARB_direct_state_access_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_direct_state_access");
        }
        if (set.contains("GL_ARB_draw_buffers") && !this.ARB_draw_buffers_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_draw_buffers");
        }
        if (set.contains("GL_ARB_draw_buffers_blend") && !this.ARB_draw_buffers_blend_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_draw_buffers_blend");
        }
        if (set.contains("GL_ARB_draw_elements_base_vertex") && !this.ARB_draw_elements_base_vertex_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_draw_elements_base_vertex");
        }
        if (set.contains("GL_ARB_draw_indirect") && !this.ARB_draw_indirect_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_draw_indirect");
        }
        if (set.contains("GL_ARB_draw_instanced") && !this.ARB_draw_instanced_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_draw_instanced");
        }
        if (set.contains("GL_ARB_framebuffer_no_attachments") && !this.ARB_framebuffer_no_attachments_initNativeFunctionAddresses(set)) {
            remove(set, "GL_ARB_framebuffer_no_attachments");
        }
        if (set.contains("GL_ARB_framebuffer_object") && !this.ARB_framebuffer_object_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_framebuffer_object");
        }
        if (set.contains("GL_ARB_geometry_shader4") && !this.ARB_geometry_shader4_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_geometry_shader4");
        }
        if (set.contains("GL_ARB_get_program_binary") && !this.ARB_get_program_binary_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_get_program_binary");
        }
        if (set.contains("GL_ARB_get_texture_sub_image") && !this.ARB_get_texture_sub_image_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_get_texture_sub_image");
        }
        if (set.contains("GL_ARB_gpu_shader_fp64") && !this.ARB_gpu_shader_fp64_initNativeFunctionAddresses(set)) {
            remove(set, "GL_ARB_gpu_shader_fp64");
        }
        if (set.contains("GL_ARB_imaging") && !this.ARB_imaging_initNativeFunctionAddresses(true)) {
            remove(set, "GL_ARB_imaging");
        }
        if (set.contains("GL_ARB_indirect_parameters") && !this.ARB_indirect_parameters_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_indirect_parameters");
        }
        if (set.contains("GL_ARB_instanced_arrays") && !this.ARB_instanced_arrays_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_instanced_arrays");
        }
        if (set.contains("GL_ARB_internalformat_query") && !this.ARB_internalformat_query_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_internalformat_query");
        }
        if (set.contains("GL_ARB_internalformat_query2") && !this.ARB_internalformat_query2_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_internalformat_query2");
        }
        if (set.contains("GL_ARB_invalidate_subdata") && !this.ARB_invalidate_subdata_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_invalidate_subdata");
        }
        if (set.contains("GL_ARB_map_buffer_range") && !this.ARB_map_buffer_range_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_map_buffer_range");
        }
        if (set.contains("GL_ARB_matrix_palette") && !this.ARB_matrix_palette_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_matrix_palette");
        }
        if (set.contains("GL_ARB_multi_bind") && !this.ARB_multi_bind_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_multi_bind");
        }
        if (set.contains("GL_ARB_multi_draw_indirect") && !this.ARB_multi_draw_indirect_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_multi_draw_indirect");
        }
        if (set.contains("GL_ARB_multisample") && !this.ARB_multisample_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_multisample");
        }
        if (set.contains("GL_ARB_multitexture") && !this.ARB_multitexture_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_multitexture");
        }
        if (set.contains("GL_ARB_occlusion_query") && !this.ARB_occlusion_query_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_occlusion_query");
        }
        if (set.contains("GL_ARB_point_parameters") && !this.ARB_point_parameters_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_point_parameters");
        }
        if (set.contains("GL_ARB_program") && !this.ARB_program_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_program");
        }
        if (set.contains("GL_ARB_program_interface_query") && !this.ARB_program_interface_query_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_program_interface_query");
        }
        if (set.contains("GL_ARB_provoking_vertex") && !this.ARB_provoking_vertex_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_provoking_vertex");
        }
        if (set.contains("GL_ARB_robustness") && !this.ARB_robustness_initNativeFunctionAddresses(true, set)) {
            remove(set, "GL_ARB_robustness");
        }
        if (set.contains("GL_ARB_sample_shading") && !this.ARB_sample_shading_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_sample_shading");
        }
        if (set.contains("GL_ARB_sampler_objects") && !this.ARB_sampler_objects_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_sampler_objects");
        }
        if (set.contains("GL_ARB_separate_shader_objects") && !this.ARB_separate_shader_objects_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_separate_shader_objects");
        }
        if (set.contains("GL_ARB_shader_atomic_counters") && !this.ARB_shader_atomic_counters_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_shader_atomic_counters");
        }
        if (set.contains("GL_ARB_shader_image_load_store") && !this.ARB_shader_image_load_store_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_shader_image_load_store");
        }
        if (set.contains("GL_ARB_shader_objects") && !this.ARB_shader_objects_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_shader_objects");
        }
        if (set.contains("GL_ARB_shader_storage_buffer_object") && !this.ARB_shader_storage_buffer_object_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_shader_storage_buffer_object");
        }
        if (set.contains("GL_ARB_shader_subroutine") && !this.ARB_shader_subroutine_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_shader_subroutine");
        }
        if (set.contains("GL_ARB_shading_language_include") && !this.ARB_shading_language_include_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_shading_language_include");
        }
        if (set.contains("GL_ARB_sparse_buffer") && !this.ARB_sparse_buffer_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_sparse_buffer");
        }
        if (set.contains("GL_ARB_sparse_texture") && !this.ARB_sparse_texture_initNativeFunctionAddresses(set)) {
            remove(set, "GL_ARB_sparse_texture");
        }
        if (set.contains("GL_ARB_sync") && !this.ARB_sync_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_sync");
        }
        if (set.contains("GL_ARB_tessellation_shader") && !this.ARB_tessellation_shader_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_tessellation_shader");
        }
        if (set.contains("GL_ARB_texture_barrier") && !this.ARB_texture_barrier_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_texture_barrier");
        }
        if (set.contains("GL_ARB_texture_buffer_object") && !this.ARB_texture_buffer_object_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_texture_buffer_object");
        }
        if (set.contains("GL_ARB_texture_buffer_range") && !this.ARB_texture_buffer_range_initNativeFunctionAddresses(set)) {
            remove(set, "GL_ARB_texture_buffer_range");
        }
        if (set.contains("GL_ARB_texture_compression") && !this.ARB_texture_compression_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_texture_compression");
        }
        if (set.contains("GL_ARB_texture_multisample") && !this.ARB_texture_multisample_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_texture_multisample");
        }
        if ((set.contains("GL_ARB_texture_storage") || set.contains("GL_EXT_texture_storage")) && !this.ARB_texture_storage_initNativeFunctionAddresses(set)) {
            remove(set, "GL_EXT_texture_storage");
            remove(set, "GL_ARB_texture_storage");
        }
        if (set.contains("GL_ARB_texture_storage_multisample") && !this.ARB_texture_storage_multisample_initNativeFunctionAddresses(set)) {
            remove(set, "GL_ARB_texture_storage_multisample");
        }
        if (set.contains("GL_ARB_texture_view") && !this.ARB_texture_view_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_texture_view");
        }
        if (set.contains("GL_ARB_timer_query") && !this.ARB_timer_query_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_timer_query");
        }
        if (set.contains("GL_ARB_transform_feedback2") && !this.ARB_transform_feedback2_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_transform_feedback2");
        }
        if (set.contains("GL_ARB_transform_feedback3") && !this.ARB_transform_feedback3_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_transform_feedback3");
        }
        if (set.contains("GL_ARB_transform_feedback_instanced") && !this.ARB_transform_feedback_instanced_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_transform_feedback_instanced");
        }
        if (set.contains("GL_ARB_transpose_matrix") && !this.ARB_transpose_matrix_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_transpose_matrix");
        }
        if (set.contains("GL_ARB_uniform_buffer_object") && !this.ARB_uniform_buffer_object_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_uniform_buffer_object");
        }
        if (set.contains("GL_ARB_vertex_array_object") && !this.ARB_vertex_array_object_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_vertex_array_object");
        }
        if (set.contains("GL_ARB_vertex_attrib_64bit") && !this.ARB_vertex_attrib_64bit_initNativeFunctionAddresses(set)) {
            remove(set, "GL_ARB_vertex_attrib_64bit");
        }
        if (set.contains("GL_ARB_vertex_attrib_binding") && !this.ARB_vertex_attrib_binding_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_vertex_attrib_binding");
        }
        if (set.contains("GL_ARB_vertex_blend") && !this.ARB_vertex_blend_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_vertex_blend");
        }
        if (set.contains("GL_ARB_vertex_program") && !this.ARB_vertex_program_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_vertex_program");
        }
        if (set.contains("GL_ARB_vertex_shader") && !this.ARB_vertex_shader_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_vertex_shader");
        }
        if (set.contains("GL_ARB_vertex_type_2_10_10_10_rev") && !this.ARB_vertex_type_2_10_10_10_rev_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_vertex_type_2_10_10_10_rev");
        }
        if (set.contains("GL_ARB_viewport_array") && !this.ARB_viewport_array_initNativeFunctionAddresses()) {
            remove(set, "GL_ARB_viewport_array");
        }
        if (set.contains("GL_ARB_window_pos") && !this.ARB_window_pos_initNativeFunctionAddresses(true)) {
            remove(set, "GL_ARB_window_pos");
        }
        if (set.contains("GL_ATI_draw_buffers") && !this.ATI_draw_buffers_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_draw_buffers");
        }
        if (set.contains("GL_ATI_element_array") && !this.ATI_element_array_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_element_array");
        }
        if (set.contains("GL_ATI_envmap_bumpmap") && !this.ATI_envmap_bumpmap_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_envmap_bumpmap");
        }
        if (set.contains("GL_ATI_fragment_shader") && !this.ATI_fragment_shader_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_fragment_shader");
        }
        if (set.contains("GL_ATI_map_object_buffer") && !this.ATI_map_object_buffer_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_map_object_buffer");
        }
        if (set.contains("GL_ATI_pn_triangles") && !this.ATI_pn_triangles_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_pn_triangles");
        }
        if (set.contains("GL_ATI_separate_stencil") && !this.ATI_separate_stencil_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_separate_stencil");
        }
        if (set.contains("GL_ATI_vertex_array_object") && !this.ATI_vertex_array_object_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_vertex_array_object");
        }
        if (set.contains("GL_ATI_vertex_attrib_array_object") && !this.ATI_vertex_attrib_array_object_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_vertex_attrib_array_object");
        }
        if (set.contains("GL_ATI_vertex_streams") && !this.ATI_vertex_streams_initNativeFunctionAddresses()) {
            remove(set, "GL_ATI_vertex_streams");
        }
        if (set.contains("GL_EXT_bindable_uniform") && !this.EXT_bindable_uniform_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_bindable_uniform");
        }
        if (set.contains("GL_EXT_blend_color") && !this.EXT_blend_color_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_blend_color");
        }
        if (set.contains("GL_EXT_blend_equation_separate") && !this.EXT_blend_equation_separate_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_blend_equation_separate");
        }
        if (set.contains("GL_EXT_blend_func_separate") && !this.EXT_blend_func_separate_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_blend_func_separate");
        }
        if (set.contains("GL_EXT_blend_minmax") && !this.EXT_blend_minmax_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_blend_minmax");
        }
        if (set.contains("GL_EXT_compiled_vertex_array") && !this.EXT_compiled_vertex_array_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_compiled_vertex_array");
        }
        if (set.contains("GL_EXT_depth_bounds_test") && !this.EXT_depth_bounds_test_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_depth_bounds_test");
        }
        set.add("GL_EXT_direct_state_access");
        if (set.contains("GL_EXT_direct_state_access") && !this.EXT_direct_state_access_initNativeFunctionAddresses(true, set)) {
            remove(set, "GL_EXT_direct_state_access");
        }
        if (set.contains("GL_EXT_draw_buffers2") && !this.EXT_draw_buffers2_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_draw_buffers2");
        }
        if (set.contains("GL_EXT_draw_instanced") && !this.EXT_draw_instanced_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_draw_instanced");
        }
        if (set.contains("GL_EXT_draw_range_elements") && !this.EXT_draw_range_elements_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_draw_range_elements");
        }
        if (set.contains("GL_EXT_fog_coord") && !this.EXT_fog_coord_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_fog_coord");
        }
        if (set.contains("GL_EXT_framebuffer_blit") && !this.EXT_framebuffer_blit_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_framebuffer_blit");
        }
        if (set.contains("GL_EXT_framebuffer_multisample") && !this.EXT_framebuffer_multisample_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_framebuffer_multisample");
        }
        if (set.contains("GL_EXT_framebuffer_object") && !this.EXT_framebuffer_object_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_framebuffer_object");
        }
        if (set.contains("GL_EXT_geometry_shader4") && !this.EXT_geometry_shader4_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_geometry_shader4");
        }
        if (set.contains("GL_EXT_gpu_program_parameters") && !this.EXT_gpu_program_parameters_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_gpu_program_parameters");
        }
        if (set.contains("GL_EXT_gpu_shader4") && !this.EXT_gpu_shader4_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_gpu_shader4");
        }
        if (set.contains("GL_EXT_multi_draw_arrays") && !this.EXT_multi_draw_arrays_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_multi_draw_arrays");
        }
        if (set.contains("GL_EXT_paletted_texture") && !this.EXT_paletted_texture_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_paletted_texture");
        }
        if (set.contains("GL_EXT_point_parameters") && !this.EXT_point_parameters_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_point_parameters");
        }
        if (set.contains("GL_EXT_provoking_vertex") && !this.EXT_provoking_vertex_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_provoking_vertex");
        }
        if (set.contains("GL_EXT_secondary_color") && !this.EXT_secondary_color_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_secondary_color");
        }
        if (set.contains("GL_EXT_separate_shader_objects") && !this.EXT_separate_shader_objects_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_separate_shader_objects");
        }
        if (set.contains("GL_EXT_shader_image_load_store") && !this.EXT_shader_image_load_store_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_shader_image_load_store");
        }
        if (set.contains("GL_EXT_stencil_clear_tag") && !this.EXT_stencil_clear_tag_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_stencil_clear_tag");
        }
        if (set.contains("GL_EXT_stencil_two_side") && !this.EXT_stencil_two_side_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_stencil_two_side");
        }
        if (set.contains("GL_EXT_texture_array") && !this.EXT_texture_array_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_texture_array");
        }
        if (set.contains("GL_EXT_texture_buffer_object") && !this.EXT_texture_buffer_object_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_texture_buffer_object");
        }
        if (set.contains("GL_EXT_texture_integer") && !this.EXT_texture_integer_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_texture_integer");
        }
        if (set.contains("GL_EXT_timer_query") && !this.EXT_timer_query_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_timer_query");
        }
        if (set.contains("GL_EXT_transform_feedback") && !this.EXT_transform_feedback_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_transform_feedback");
        }
        if (set.contains("GL_EXT_vertex_attrib_64bit") && !this.EXT_vertex_attrib_64bit_initNativeFunctionAddresses(set)) {
            remove(set, "GL_EXT_vertex_attrib_64bit");
        }
        if (set.contains("GL_EXT_vertex_shader") && !this.EXT_vertex_shader_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_vertex_shader");
        }
        if (set.contains("GL_EXT_vertex_weighting") && !this.EXT_vertex_weighting_initNativeFunctionAddresses()) {
            remove(set, "GL_EXT_vertex_weighting");
        }
        if (set.contains("OpenGL12") && !this.GL12_initNativeFunctionAddresses()) {
            remove(set, "OpenGL12");
        }
        if (set.contains("OpenGL13") && !this.GL13_initNativeFunctionAddresses(true)) {
            remove(set, "OpenGL13");
        }
        if (set.contains("OpenGL14") && !this.GL14_initNativeFunctionAddresses(true)) {
            remove(set, "OpenGL14");
        }
        if (set.contains("OpenGL15") && !this.GL15_initNativeFunctionAddresses()) {
            remove(set, "OpenGL15");
        }
        if (set.contains("OpenGL20") && !this.GL20_initNativeFunctionAddresses()) {
            remove(set, "OpenGL20");
        }
        if (set.contains("OpenGL21") && !this.GL21_initNativeFunctionAddresses()) {
            remove(set, "OpenGL21");
        }
        if (set.contains("OpenGL30") && !this.GL30_initNativeFunctionAddresses()) {
            remove(set, "OpenGL30");
        }
        if (set.contains("OpenGL31") && !this.GL31_initNativeFunctionAddresses()) {
            remove(set, "OpenGL31");
        }
        if (set.contains("OpenGL32") && !this.GL32_initNativeFunctionAddresses()) {
            remove(set, "OpenGL32");
        }
        if (set.contains("OpenGL33") && !this.GL33_initNativeFunctionAddresses(true)) {
            remove(set, "OpenGL33");
        }
        if (set.contains("OpenGL40") && !this.GL40_initNativeFunctionAddresses()) {
            remove(set, "OpenGL40");
        }
        if (set.contains("OpenGL41") && !this.GL41_initNativeFunctionAddresses()) {
            remove(set, "OpenGL41");
        }
        if (set.contains("OpenGL42") && !this.GL42_initNativeFunctionAddresses()) {
            remove(set, "OpenGL42");
        }
        if (set.contains("OpenGL43") && !this.GL43_initNativeFunctionAddresses()) {
            remove(set, "OpenGL43");
        }
        if (set.contains("OpenGL44") && !this.GL44_initNativeFunctionAddresses()) {
            remove(set, "OpenGL44");
        }
        if (set.contains("OpenGL45") && !this.GL45_initNativeFunctionAddresses()) {
            remove(set, "OpenGL45");
        }
        if (set.contains("GL_GREMEDY_frame_terminator") && !this.GREMEDY_frame_terminator_initNativeFunctionAddresses()) {
            remove(set, "GL_GREMEDY_frame_terminator");
        }
        if (set.contains("GL_GREMEDY_string_marker") && !this.GREMEDY_string_marker_initNativeFunctionAddresses()) {
            remove(set, "GL_GREMEDY_string_marker");
        }
        if (set.contains("GL_INTEL_map_texture") && !this.INTEL_map_texture_initNativeFunctionAddresses()) {
            remove(set, "GL_INTEL_map_texture");
        }
        if (set.contains("GL_KHR_debug") && !this.KHR_debug_initNativeFunctionAddresses()) {
            remove(set, "GL_KHR_debug");
        }
        if (set.contains("GL_KHR_robustness") && !this.KHR_robustness_initNativeFunctionAddresses()) {
            remove(set, "GL_KHR_robustness");
        }
        if (set.contains("GL_NV_bindless_multi_draw_indirect") && !this.NV_bindless_multi_draw_indirect_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_bindless_multi_draw_indirect");
        }
        if (set.contains("GL_NV_bindless_texture") && !this.NV_bindless_texture_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_bindless_texture");
        }
        if (set.contains("GL_NV_blend_equation_advanced") && !this.NV_blend_equation_advanced_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_blend_equation_advanced");
        }
        if (set.contains("GL_NV_conditional_render") && !this.NV_conditional_render_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_conditional_render");
        }
        if (set.contains("GL_NV_copy_image") && !this.NV_copy_image_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_copy_image");
        }
        if (set.contains("GL_NV_depth_buffer_float") && !this.NV_depth_buffer_float_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_depth_buffer_float");
        }
        if (set.contains("GL_NV_draw_texture") && !this.NV_draw_texture_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_draw_texture");
        }
        if (set.contains("GL_NV_evaluators") && !this.NV_evaluators_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_evaluators");
        }
        if (set.contains("GL_NV_explicit_multisample") && !this.NV_explicit_multisample_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_explicit_multisample");
        }
        if (set.contains("GL_NV_fence") && !this.NV_fence_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_fence");
        }
        if (set.contains("GL_NV_fragment_program") && !this.NV_fragment_program_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_fragment_program");
        }
        if (set.contains("GL_NV_framebuffer_multisample_coverage") && !this.NV_framebuffer_multisample_coverage_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_framebuffer_multisample_coverage");
        }
        if (set.contains("GL_NV_geometry_program4") && !this.NV_geometry_program4_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_geometry_program4");
        }
        if (set.contains("GL_NV_gpu_program4") && !this.NV_gpu_program4_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_gpu_program4");
        }
        if (set.contains("GL_NV_gpu_shader5") && !this.NV_gpu_shader5_initNativeFunctionAddresses(set)) {
            remove(set, "GL_NV_gpu_shader5");
        }
        if (set.contains("GL_NV_half_float") && !this.NV_half_float_initNativeFunctionAddresses(set)) {
            remove(set, "GL_NV_half_float");
        }
        if (set.contains("GL_NV_occlusion_query") && !this.NV_occlusion_query_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_occlusion_query");
        }
        if (set.contains("GL_NV_parameter_buffer_object") && !this.NV_parameter_buffer_object_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_parameter_buffer_object");
        }
        if (set.contains("GL_NV_path_rendering") && !this.NV_path_rendering_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_path_rendering");
        }
        if (set.contains("GL_NV_pixel_data_range") && !this.NV_pixel_data_range_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_pixel_data_range");
        }
        if (set.contains("GL_NV_point_sprite") && !this.NV_point_sprite_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_point_sprite");
        }
        if (set.contains("GL_NV_present_video") && !this.NV_present_video_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_present_video");
        }
        set.add("GL_NV_primitive_restart");
        if (set.contains("GL_NV_primitive_restart") && !this.NV_primitive_restart_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_primitive_restart");
        }
        if (set.contains("GL_NV_program") && !this.NV_program_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_program");
        }
        if (set.contains("GL_NV_register_combiners") && !this.NV_register_combiners_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_register_combiners");
        }
        if (set.contains("GL_NV_register_combiners2") && !this.NV_register_combiners2_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_register_combiners2");
        }
        if (set.contains("GL_NV_shader_buffer_load") && !this.NV_shader_buffer_load_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_shader_buffer_load");
        }
        if (set.contains("GL_NV_texture_barrier") && !this.NV_texture_barrier_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_texture_barrier");
        }
        if (set.contains("GL_NV_texture_multisample") && !this.NV_texture_multisample_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_texture_multisample");
        }
        if (set.contains("GL_NV_transform_feedback") && !this.NV_transform_feedback_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_transform_feedback");
        }
        if (set.contains("GL_NV_transform_feedback2") && !this.NV_transform_feedback2_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_transform_feedback2");
        }
        if (set.contains("GL_NV_vertex_array_range") && !this.NV_vertex_array_range_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_vertex_array_range");
        }
        if (set.contains("GL_NV_vertex_attrib_integer_64bit") && !this.NV_vertex_attrib_integer_64bit_initNativeFunctionAddresses(set)) {
            remove(set, "GL_NV_vertex_attrib_integer_64bit");
        }
        if (set.contains("GL_NV_vertex_buffer_unified_memory") && !this.NV_vertex_buffer_unified_memory_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_vertex_buffer_unified_memory");
        }
        if (set.contains("GL_NV_vertex_program") && !this.NV_vertex_program_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_vertex_program");
        }
        if (set.contains("GL_NV_video_capture") && !this.NV_video_capture_initNativeFunctionAddresses()) {
            remove(set, "GL_NV_video_capture");
        }
        return set;
    }
    
    static void unloadAllStubs() {
    }
    
    ContextCapabilities(final boolean b) throws LWJGLException {
        this.util = new APIUtil();
        this.tracker = new StateTracker();
        final Set initAllStubs = this.initAllStubs(b);
        this.GL_AMD_blend_minmax_factor = initAllStubs.contains("GL_AMD_blend_minmax_factor");
        this.GL_AMD_conservative_depth = initAllStubs.contains("GL_AMD_conservative_depth");
        this.GL_AMD_debug_output = (initAllStubs.contains("GL_AMD_debug_output") || initAllStubs.contains("GL_AMDX_debug_output"));
        this.GL_AMD_depth_clamp_separate = initAllStubs.contains("GL_AMD_depth_clamp_separate");
        this.GL_AMD_draw_buffers_blend = initAllStubs.contains("GL_AMD_draw_buffers_blend");
        this.GL_AMD_interleaved_elements = initAllStubs.contains("GL_AMD_interleaved_elements");
        this.GL_AMD_multi_draw_indirect = initAllStubs.contains("GL_AMD_multi_draw_indirect");
        this.GL_AMD_name_gen_delete = initAllStubs.contains("GL_AMD_name_gen_delete");
        this.GL_AMD_performance_monitor = initAllStubs.contains("GL_AMD_performance_monitor");
        this.GL_AMD_pinned_memory = initAllStubs.contains("GL_AMD_pinned_memory");
        this.GL_AMD_query_buffer_object = initAllStubs.contains("GL_AMD_query_buffer_object");
        this.GL_AMD_sample_positions = initAllStubs.contains("GL_AMD_sample_positions");
        this.GL_AMD_seamless_cubemap_per_texture = initAllStubs.contains("GL_AMD_seamless_cubemap_per_texture");
        this.GL_AMD_shader_atomic_counter_ops = initAllStubs.contains("GL_AMD_shader_atomic_counter_ops");
        this.GL_AMD_shader_stencil_export = initAllStubs.contains("GL_AMD_shader_stencil_export");
        this.GL_AMD_shader_trinary_minmax = initAllStubs.contains("GL_AMD_shader_trinary_minmax");
        this.GL_AMD_sparse_texture = initAllStubs.contains("GL_AMD_sparse_texture");
        this.GL_AMD_stencil_operation_extended = initAllStubs.contains("GL_AMD_stencil_operation_extended");
        this.GL_AMD_texture_texture4 = initAllStubs.contains("GL_AMD_texture_texture4");
        this.GL_AMD_transform_feedback3_lines_triangles = initAllStubs.contains("GL_AMD_transform_feedback3_lines_triangles");
        this.GL_AMD_vertex_shader_layer = initAllStubs.contains("GL_AMD_vertex_shader_layer");
        this.GL_AMD_vertex_shader_tessellator = initAllStubs.contains("GL_AMD_vertex_shader_tessellator");
        this.GL_AMD_vertex_shader_viewport_index = initAllStubs.contains("GL_AMD_vertex_shader_viewport_index");
        this.GL_APPLE_aux_depth_stencil = initAllStubs.contains("GL_APPLE_aux_depth_stencil");
        this.GL_APPLE_client_storage = initAllStubs.contains("GL_APPLE_client_storage");
        this.GL_APPLE_element_array = initAllStubs.contains("GL_APPLE_element_array");
        this.GL_APPLE_fence = initAllStubs.contains("GL_APPLE_fence");
        this.GL_APPLE_float_pixels = initAllStubs.contains("GL_APPLE_float_pixels");
        this.GL_APPLE_flush_buffer_range = initAllStubs.contains("GL_APPLE_flush_buffer_range");
        this.GL_APPLE_object_purgeable = initAllStubs.contains("GL_APPLE_object_purgeable");
        this.GL_APPLE_packed_pixels = initAllStubs.contains("GL_APPLE_packed_pixels");
        this.GL_APPLE_rgb_422 = initAllStubs.contains("GL_APPLE_rgb_422");
        this.GL_APPLE_row_bytes = initAllStubs.contains("GL_APPLE_row_bytes");
        this.GL_APPLE_texture_range = initAllStubs.contains("GL_APPLE_texture_range");
        this.GL_APPLE_vertex_array_object = initAllStubs.contains("GL_APPLE_vertex_array_object");
        this.GL_APPLE_vertex_array_range = initAllStubs.contains("GL_APPLE_vertex_array_range");
        this.GL_APPLE_vertex_program_evaluators = initAllStubs.contains("GL_APPLE_vertex_program_evaluators");
        this.GL_APPLE_ycbcr_422 = initAllStubs.contains("GL_APPLE_ycbcr_422");
        this.GL_ARB_ES2_compatibility = initAllStubs.contains("GL_ARB_ES2_compatibility");
        this.GL_ARB_ES3_1_compatibility = initAllStubs.contains("GL_ARB_ES3_1_compatibility");
        this.GL_ARB_ES3_compatibility = initAllStubs.contains("GL_ARB_ES3_compatibility");
        this.GL_ARB_arrays_of_arrays = initAllStubs.contains("GL_ARB_arrays_of_arrays");
        this.GL_ARB_base_instance = initAllStubs.contains("GL_ARB_base_instance");
        this.GL_ARB_bindless_texture = initAllStubs.contains("GL_ARB_bindless_texture");
        this.GL_ARB_blend_func_extended = initAllStubs.contains("GL_ARB_blend_func_extended");
        this.GL_ARB_buffer_storage = initAllStubs.contains("GL_ARB_buffer_storage");
        this.GL_ARB_cl_event = initAllStubs.contains("GL_ARB_cl_event");
        this.GL_ARB_clear_buffer_object = initAllStubs.contains("GL_ARB_clear_buffer_object");
        this.GL_ARB_clear_texture = initAllStubs.contains("GL_ARB_clear_texture");
        this.GL_ARB_clip_control = initAllStubs.contains("GL_ARB_clip_control");
        this.GL_ARB_color_buffer_float = initAllStubs.contains("GL_ARB_color_buffer_float");
        this.GL_ARB_compatibility = initAllStubs.contains("GL_ARB_compatibility");
        this.GL_ARB_compressed_texture_pixel_storage = initAllStubs.contains("GL_ARB_compressed_texture_pixel_storage");
        this.GL_ARB_compute_shader = initAllStubs.contains("GL_ARB_compute_shader");
        this.GL_ARB_compute_variable_group_size = initAllStubs.contains("GL_ARB_compute_variable_group_size");
        this.GL_ARB_conditional_render_inverted = initAllStubs.contains("GL_ARB_conditional_render_inverted");
        this.GL_ARB_conservative_depth = initAllStubs.contains("GL_ARB_conservative_depth");
        this.GL_ARB_copy_buffer = initAllStubs.contains("GL_ARB_copy_buffer");
        this.GL_ARB_copy_image = initAllStubs.contains("GL_ARB_copy_image");
        this.GL_ARB_cull_distance = initAllStubs.contains("GL_ARB_cull_distance");
        this.GL_ARB_debug_output = initAllStubs.contains("GL_ARB_debug_output");
        this.GL_ARB_depth_buffer_float = initAllStubs.contains("GL_ARB_depth_buffer_float");
        this.GL_ARB_depth_clamp = initAllStubs.contains("GL_ARB_depth_clamp");
        this.GL_ARB_depth_texture = initAllStubs.contains("GL_ARB_depth_texture");
        this.GL_ARB_derivative_control = initAllStubs.contains("GL_ARB_derivative_control");
        this.GL_ARB_direct_state_access = initAllStubs.contains("GL_ARB_direct_state_access");
        this.GL_ARB_draw_buffers = initAllStubs.contains("GL_ARB_draw_buffers");
        this.GL_ARB_draw_buffers_blend = initAllStubs.contains("GL_ARB_draw_buffers_blend");
        this.GL_ARB_draw_elements_base_vertex = initAllStubs.contains("GL_ARB_draw_elements_base_vertex");
        this.GL_ARB_draw_indirect = initAllStubs.contains("GL_ARB_draw_indirect");
        this.GL_ARB_draw_instanced = initAllStubs.contains("GL_ARB_draw_instanced");
        this.GL_ARB_enhanced_layouts = initAllStubs.contains("GL_ARB_enhanced_layouts");
        this.GL_ARB_explicit_attrib_location = initAllStubs.contains("GL_ARB_explicit_attrib_location");
        this.GL_ARB_explicit_uniform_location = initAllStubs.contains("GL_ARB_explicit_uniform_location");
        this.GL_ARB_fragment_coord_conventions = initAllStubs.contains("GL_ARB_fragment_coord_conventions");
        this.GL_ARB_fragment_layer_viewport = initAllStubs.contains("GL_ARB_fragment_layer_viewport");
        this.GL_ARB_fragment_program = (initAllStubs.contains("GL_ARB_fragment_program") && initAllStubs.contains("GL_ARB_program"));
        this.GL_ARB_fragment_program_shadow = initAllStubs.contains("GL_ARB_fragment_program_shadow");
        this.GL_ARB_fragment_shader = initAllStubs.contains("GL_ARB_fragment_shader");
        this.GL_ARB_framebuffer_no_attachments = initAllStubs.contains("GL_ARB_framebuffer_no_attachments");
        this.GL_ARB_framebuffer_object = initAllStubs.contains("GL_ARB_framebuffer_object");
        this.GL_ARB_framebuffer_sRGB = initAllStubs.contains("GL_ARB_framebuffer_sRGB");
        this.GL_ARB_geometry_shader4 = initAllStubs.contains("GL_ARB_geometry_shader4");
        this.GL_ARB_get_program_binary = initAllStubs.contains("GL_ARB_get_program_binary");
        this.GL_ARB_get_texture_sub_image = initAllStubs.contains("GL_ARB_get_texture_sub_image");
        this.GL_ARB_gpu_shader5 = initAllStubs.contains("GL_ARB_gpu_shader5");
        this.GL_ARB_gpu_shader_fp64 = initAllStubs.contains("GL_ARB_gpu_shader_fp64");
        this.GL_ARB_half_float_pixel = initAllStubs.contains("GL_ARB_half_float_pixel");
        this.GL_ARB_half_float_vertex = initAllStubs.contains("GL_ARB_half_float_vertex");
        this.GL_ARB_imaging = initAllStubs.contains("GL_ARB_imaging");
        this.GL_ARB_indirect_parameters = initAllStubs.contains("GL_ARB_indirect_parameters");
        this.GL_ARB_instanced_arrays = initAllStubs.contains("GL_ARB_instanced_arrays");
        this.GL_ARB_internalformat_query = initAllStubs.contains("GL_ARB_internalformat_query");
        this.GL_ARB_internalformat_query2 = initAllStubs.contains("GL_ARB_internalformat_query2");
        this.GL_ARB_invalidate_subdata = initAllStubs.contains("GL_ARB_invalidate_subdata");
        this.GL_ARB_map_buffer_alignment = initAllStubs.contains("GL_ARB_map_buffer_alignment");
        this.GL_ARB_map_buffer_range = initAllStubs.contains("GL_ARB_map_buffer_range");
        this.GL_ARB_matrix_palette = initAllStubs.contains("GL_ARB_matrix_palette");
        this.GL_ARB_multi_bind = initAllStubs.contains("GL_ARB_multi_bind");
        this.GL_ARB_multi_draw_indirect = initAllStubs.contains("GL_ARB_multi_draw_indirect");
        this.GL_ARB_multisample = initAllStubs.contains("GL_ARB_multisample");
        this.GL_ARB_multitexture = initAllStubs.contains("GL_ARB_multitexture");
        this.GL_ARB_occlusion_query = initAllStubs.contains("GL_ARB_occlusion_query");
        this.GL_ARB_occlusion_query2 = initAllStubs.contains("GL_ARB_occlusion_query2");
        this.GL_ARB_pipeline_statistics_query = initAllStubs.contains("GL_ARB_pipeline_statistics_query");
        this.GL_ARB_pixel_buffer_object = (initAllStubs.contains("GL_ARB_pixel_buffer_object") && initAllStubs.contains("GL_ARB_buffer_object"));
        this.GL_ARB_point_parameters = initAllStubs.contains("GL_ARB_point_parameters");
        this.GL_ARB_point_sprite = initAllStubs.contains("GL_ARB_point_sprite");
        this.GL_ARB_program_interface_query = initAllStubs.contains("GL_ARB_program_interface_query");
        this.GL_ARB_provoking_vertex = initAllStubs.contains("GL_ARB_provoking_vertex");
        this.GL_ARB_query_buffer_object = initAllStubs.contains("GL_ARB_query_buffer_object");
        this.GL_ARB_robust_buffer_access_behavior = initAllStubs.contains("GL_ARB_robust_buffer_access_behavior");
        this.GL_ARB_robustness = initAllStubs.contains("GL_ARB_robustness");
        this.GL_ARB_robustness_isolation = initAllStubs.contains("GL_ARB_robustness_isolation");
        this.GL_ARB_sample_shading = initAllStubs.contains("GL_ARB_sample_shading");
        this.GL_ARB_sampler_objects = initAllStubs.contains("GL_ARB_sampler_objects");
        this.GL_ARB_seamless_cube_map = initAllStubs.contains("GL_ARB_seamless_cube_map");
        this.GL_ARB_seamless_cubemap_per_texture = initAllStubs.contains("GL_ARB_seamless_cubemap_per_texture");
        this.GL_ARB_separate_shader_objects = initAllStubs.contains("GL_ARB_separate_shader_objects");
        this.GL_ARB_shader_atomic_counters = initAllStubs.contains("GL_ARB_shader_atomic_counters");
        this.GL_ARB_shader_bit_encoding = initAllStubs.contains("GL_ARB_shader_bit_encoding");
        this.GL_ARB_shader_draw_parameters = initAllStubs.contains("GL_ARB_shader_draw_parameters");
        this.GL_ARB_shader_group_vote = initAllStubs.contains("GL_ARB_shader_group_vote");
        this.GL_ARB_shader_image_load_store = initAllStubs.contains("GL_ARB_shader_image_load_store");
        this.GL_ARB_shader_image_size = initAllStubs.contains("GL_ARB_shader_image_size");
        this.GL_ARB_shader_objects = initAllStubs.contains("GL_ARB_shader_objects");
        this.GL_ARB_shader_precision = initAllStubs.contains("GL_ARB_shader_precision");
        this.GL_ARB_shader_stencil_export = initAllStubs.contains("GL_ARB_shader_stencil_export");
        this.GL_ARB_shader_storage_buffer_object = initAllStubs.contains("GL_ARB_shader_storage_buffer_object");
        this.GL_ARB_shader_subroutine = initAllStubs.contains("GL_ARB_shader_subroutine");
        this.GL_ARB_shader_texture_image_samples = initAllStubs.contains("GL_ARB_shader_texture_image_samples");
        this.GL_ARB_shader_texture_lod = initAllStubs.contains("GL_ARB_shader_texture_lod");
        this.GL_ARB_shading_language_100 = initAllStubs.contains("GL_ARB_shading_language_100");
        this.GL_ARB_shading_language_420pack = initAllStubs.contains("GL_ARB_shading_language_420pack");
        this.GL_ARB_shading_language_include = initAllStubs.contains("GL_ARB_shading_language_include");
        this.GL_ARB_shading_language_packing = initAllStubs.contains("GL_ARB_shading_language_packing");
        this.GL_ARB_shadow = initAllStubs.contains("GL_ARB_shadow");
        this.GL_ARB_shadow_ambient = initAllStubs.contains("GL_ARB_shadow_ambient");
        this.GL_ARB_sparse_buffer = initAllStubs.contains("GL_ARB_sparse_buffer");
        this.GL_ARB_sparse_texture = initAllStubs.contains("GL_ARB_sparse_texture");
        this.GL_ARB_stencil_texturing = initAllStubs.contains("GL_ARB_stencil_texturing");
        this.GL_ARB_sync = initAllStubs.contains("GL_ARB_sync");
        this.GL_ARB_tessellation_shader = initAllStubs.contains("GL_ARB_tessellation_shader");
        this.GL_ARB_texture_barrier = initAllStubs.contains("GL_ARB_texture_barrier");
        this.GL_ARB_texture_border_clamp = initAllStubs.contains("GL_ARB_texture_border_clamp");
        this.GL_ARB_texture_buffer_object = initAllStubs.contains("GL_ARB_texture_buffer_object");
        this.GL_ARB_texture_buffer_object_rgb32 = (initAllStubs.contains("GL_ARB_texture_buffer_object_rgb32") || initAllStubs.contains("GL_EXT_texture_buffer_object_rgb32"));
        this.GL_ARB_texture_buffer_range = initAllStubs.contains("GL_ARB_texture_buffer_range");
        this.GL_ARB_texture_compression = initAllStubs.contains("GL_ARB_texture_compression");
        this.GL_ARB_texture_compression_bptc = (initAllStubs.contains("GL_ARB_texture_compression_bptc") || initAllStubs.contains("GL_EXT_texture_compression_bptc"));
        this.GL_ARB_texture_compression_rgtc = initAllStubs.contains("GL_ARB_texture_compression_rgtc");
        this.GL_ARB_texture_cube_map = initAllStubs.contains("GL_ARB_texture_cube_map");
        this.GL_ARB_texture_cube_map_array = initAllStubs.contains("GL_ARB_texture_cube_map_array");
        this.GL_ARB_texture_env_add = initAllStubs.contains("GL_ARB_texture_env_add");
        this.GL_ARB_texture_env_combine = initAllStubs.contains("GL_ARB_texture_env_combine");
        this.GL_ARB_texture_env_crossbar = initAllStubs.contains("GL_ARB_texture_env_crossbar");
        this.GL_ARB_texture_env_dot3 = initAllStubs.contains("GL_ARB_texture_env_dot3");
        this.GL_ARB_texture_float = initAllStubs.contains("GL_ARB_texture_float");
        this.GL_ARB_texture_gather = initAllStubs.contains("GL_ARB_texture_gather");
        this.GL_ARB_texture_mirror_clamp_to_edge = initAllStubs.contains("GL_ARB_texture_mirror_clamp_to_edge");
        this.GL_ARB_texture_mirrored_repeat = initAllStubs.contains("GL_ARB_texture_mirrored_repeat");
        this.GL_ARB_texture_multisample = initAllStubs.contains("GL_ARB_texture_multisample");
        this.GL_ARB_texture_non_power_of_two = initAllStubs.contains("GL_ARB_texture_non_power_of_two");
        this.GL_ARB_texture_query_levels = initAllStubs.contains("GL_ARB_texture_query_levels");
        this.GL_ARB_texture_query_lod = initAllStubs.contains("GL_ARB_texture_query_lod");
        this.GL_ARB_texture_rectangle = initAllStubs.contains("GL_ARB_texture_rectangle");
        this.GL_ARB_texture_rg = initAllStubs.contains("GL_ARB_texture_rg");
        this.GL_ARB_texture_rgb10_a2ui = initAllStubs.contains("GL_ARB_texture_rgb10_a2ui");
        this.GL_ARB_texture_stencil8 = initAllStubs.contains("GL_ARB_texture_stencil8");
        this.GL_ARB_texture_storage = (initAllStubs.contains("GL_ARB_texture_storage") || initAllStubs.contains("GL_EXT_texture_storage"));
        this.GL_ARB_texture_storage_multisample = initAllStubs.contains("GL_ARB_texture_storage_multisample");
        this.GL_ARB_texture_swizzle = initAllStubs.contains("GL_ARB_texture_swizzle");
        this.GL_ARB_texture_view = initAllStubs.contains("GL_ARB_texture_view");
        this.GL_ARB_timer_query = initAllStubs.contains("GL_ARB_timer_query");
        this.GL_ARB_transform_feedback2 = initAllStubs.contains("GL_ARB_transform_feedback2");
        this.GL_ARB_transform_feedback3 = initAllStubs.contains("GL_ARB_transform_feedback3");
        this.GL_ARB_transform_feedback_instanced = initAllStubs.contains("GL_ARB_transform_feedback_instanced");
        this.GL_ARB_transform_feedback_overflow_query = initAllStubs.contains("GL_ARB_transform_feedback_overflow_query");
        this.GL_ARB_transpose_matrix = initAllStubs.contains("GL_ARB_transpose_matrix");
        this.GL_ARB_uniform_buffer_object = initAllStubs.contains("GL_ARB_uniform_buffer_object");
        this.GL_ARB_vertex_array_bgra = initAllStubs.contains("GL_ARB_vertex_array_bgra");
        this.GL_ARB_vertex_array_object = initAllStubs.contains("GL_ARB_vertex_array_object");
        this.GL_ARB_vertex_attrib_64bit = initAllStubs.contains("GL_ARB_vertex_attrib_64bit");
        this.GL_ARB_vertex_attrib_binding = initAllStubs.contains("GL_ARB_vertex_attrib_binding");
        this.GL_ARB_vertex_blend = initAllStubs.contains("GL_ARB_vertex_blend");
        this.GL_ARB_vertex_buffer_object = (initAllStubs.contains("GL_ARB_vertex_buffer_object") && initAllStubs.contains("GL_ARB_buffer_object"));
        this.GL_ARB_vertex_program = (initAllStubs.contains("GL_ARB_vertex_program") && initAllStubs.contains("GL_ARB_program"));
        this.GL_ARB_vertex_shader = initAllStubs.contains("GL_ARB_vertex_shader");
        this.GL_ARB_vertex_type_10f_11f_11f_rev = initAllStubs.contains("GL_ARB_vertex_type_10f_11f_11f_rev");
        this.GL_ARB_vertex_type_2_10_10_10_rev = initAllStubs.contains("GL_ARB_vertex_type_2_10_10_10_rev");
        this.GL_ARB_viewport_array = initAllStubs.contains("GL_ARB_viewport_array");
        this.GL_ARB_window_pos = initAllStubs.contains("GL_ARB_window_pos");
        this.GL_ATI_draw_buffers = initAllStubs.contains("GL_ATI_draw_buffers");
        this.GL_ATI_element_array = initAllStubs.contains("GL_ATI_element_array");
        this.GL_ATI_envmap_bumpmap = initAllStubs.contains("GL_ATI_envmap_bumpmap");
        this.GL_ATI_fragment_shader = initAllStubs.contains("GL_ATI_fragment_shader");
        this.GL_ATI_map_object_buffer = initAllStubs.contains("GL_ATI_map_object_buffer");
        this.GL_ATI_meminfo = initAllStubs.contains("GL_ATI_meminfo");
        this.GL_ATI_pn_triangles = initAllStubs.contains("GL_ATI_pn_triangles");
        this.GL_ATI_separate_stencil = initAllStubs.contains("GL_ATI_separate_stencil");
        this.GL_ATI_shader_texture_lod = initAllStubs.contains("GL_ATI_shader_texture_lod");
        this.GL_ATI_text_fragment_shader = initAllStubs.contains("GL_ATI_text_fragment_shader");
        this.GL_ATI_texture_compression_3dc = initAllStubs.contains("GL_ATI_texture_compression_3dc");
        this.GL_ATI_texture_env_combine3 = initAllStubs.contains("GL_ATI_texture_env_combine3");
        this.GL_ATI_texture_float = initAllStubs.contains("GL_ATI_texture_float");
        this.GL_ATI_texture_mirror_once = initAllStubs.contains("GL_ATI_texture_mirror_once");
        this.GL_ATI_vertex_array_object = initAllStubs.contains("GL_ATI_vertex_array_object");
        this.GL_ATI_vertex_attrib_array_object = initAllStubs.contains("GL_ATI_vertex_attrib_array_object");
        this.GL_ATI_vertex_streams = initAllStubs.contains("GL_ATI_vertex_streams");
        this.GL_EXT_Cg_shader = initAllStubs.contains("GL_EXT_Cg_shader");
        this.GL_EXT_abgr = initAllStubs.contains("GL_EXT_abgr");
        this.GL_EXT_bgra = initAllStubs.contains("GL_EXT_bgra");
        this.GL_EXT_bindable_uniform = initAllStubs.contains("GL_EXT_bindable_uniform");
        this.GL_EXT_blend_color = initAllStubs.contains("GL_EXT_blend_color");
        this.GL_EXT_blend_equation_separate = initAllStubs.contains("GL_EXT_blend_equation_separate");
        this.GL_EXT_blend_func_separate = initAllStubs.contains("GL_EXT_blend_func_separate");
        this.GL_EXT_blend_minmax = initAllStubs.contains("GL_EXT_blend_minmax");
        this.GL_EXT_blend_subtract = initAllStubs.contains("GL_EXT_blend_subtract");
        this.GL_EXT_compiled_vertex_array = initAllStubs.contains("GL_EXT_compiled_vertex_array");
        this.GL_EXT_depth_bounds_test = initAllStubs.contains("GL_EXT_depth_bounds_test");
        this.GL_EXT_direct_state_access = initAllStubs.contains("GL_EXT_direct_state_access");
        this.GL_EXT_draw_buffers2 = initAllStubs.contains("GL_EXT_draw_buffers2");
        this.GL_EXT_draw_instanced = initAllStubs.contains("GL_EXT_draw_instanced");
        this.GL_EXT_draw_range_elements = initAllStubs.contains("GL_EXT_draw_range_elements");
        this.GL_EXT_fog_coord = initAllStubs.contains("GL_EXT_fog_coord");
        this.GL_EXT_framebuffer_blit = initAllStubs.contains("GL_EXT_framebuffer_blit");
        this.GL_EXT_framebuffer_multisample = initAllStubs.contains("GL_EXT_framebuffer_multisample");
        this.GL_EXT_framebuffer_multisample_blit_scaled = initAllStubs.contains("GL_EXT_framebuffer_multisample_blit_scaled");
        this.GL_EXT_framebuffer_object = initAllStubs.contains("GL_EXT_framebuffer_object");
        this.GL_EXT_framebuffer_sRGB = initAllStubs.contains("GL_EXT_framebuffer_sRGB");
        this.GL_EXT_geometry_shader4 = initAllStubs.contains("GL_EXT_geometry_shader4");
        this.GL_EXT_gpu_program_parameters = initAllStubs.contains("GL_EXT_gpu_program_parameters");
        this.GL_EXT_gpu_shader4 = initAllStubs.contains("GL_EXT_gpu_shader4");
        this.GL_EXT_multi_draw_arrays = initAllStubs.contains("GL_EXT_multi_draw_arrays");
        this.GL_EXT_packed_depth_stencil = initAllStubs.contains("GL_EXT_packed_depth_stencil");
        this.GL_EXT_packed_float = initAllStubs.contains("GL_EXT_packed_float");
        this.GL_EXT_packed_pixels = initAllStubs.contains("GL_EXT_packed_pixels");
        this.GL_EXT_paletted_texture = initAllStubs.contains("GL_EXT_paletted_texture");
        this.GL_EXT_pixel_buffer_object = (initAllStubs.contains("GL_EXT_pixel_buffer_object") && initAllStubs.contains("GL_ARB_buffer_object"));
        this.GL_EXT_point_parameters = initAllStubs.contains("GL_EXT_point_parameters");
        this.GL_EXT_provoking_vertex = initAllStubs.contains("GL_EXT_provoking_vertex");
        this.GL_EXT_rescale_normal = initAllStubs.contains("GL_EXT_rescale_normal");
        this.GL_EXT_secondary_color = initAllStubs.contains("GL_EXT_secondary_color");
        this.GL_EXT_separate_shader_objects = initAllStubs.contains("GL_EXT_separate_shader_objects");
        this.GL_EXT_separate_specular_color = initAllStubs.contains("GL_EXT_separate_specular_color");
        this.GL_EXT_shader_image_load_store = initAllStubs.contains("GL_EXT_shader_image_load_store");
        this.GL_EXT_shadow_funcs = initAllStubs.contains("GL_EXT_shadow_funcs");
        this.GL_EXT_shared_texture_palette = initAllStubs.contains("GL_EXT_shared_texture_palette");
        this.GL_EXT_stencil_clear_tag = initAllStubs.contains("GL_EXT_stencil_clear_tag");
        this.GL_EXT_stencil_two_side = initAllStubs.contains("GL_EXT_stencil_two_side");
        this.GL_EXT_stencil_wrap = initAllStubs.contains("GL_EXT_stencil_wrap");
        this.GL_EXT_texture_3d = initAllStubs.contains("GL_EXT_texture_3d");
        this.GL_EXT_texture_array = initAllStubs.contains("GL_EXT_texture_array");
        this.GL_EXT_texture_buffer_object = initAllStubs.contains("GL_EXT_texture_buffer_object");
        this.GL_EXT_texture_compression_latc = initAllStubs.contains("GL_EXT_texture_compression_latc");
        this.GL_EXT_texture_compression_rgtc = initAllStubs.contains("GL_EXT_texture_compression_rgtc");
        this.GL_EXT_texture_compression_s3tc = initAllStubs.contains("GL_EXT_texture_compression_s3tc");
        this.GL_EXT_texture_env_combine = initAllStubs.contains("GL_EXT_texture_env_combine");
        this.GL_EXT_texture_env_dot3 = initAllStubs.contains("GL_EXT_texture_env_dot3");
        this.GL_EXT_texture_filter_anisotropic = initAllStubs.contains("GL_EXT_texture_filter_anisotropic");
        this.GL_EXT_texture_integer = initAllStubs.contains("GL_EXT_texture_integer");
        this.GL_EXT_texture_lod_bias = initAllStubs.contains("GL_EXT_texture_lod_bias");
        this.GL_EXT_texture_mirror_clamp = initAllStubs.contains("GL_EXT_texture_mirror_clamp");
        this.GL_EXT_texture_rectangle = initAllStubs.contains("GL_EXT_texture_rectangle");
        this.GL_EXT_texture_sRGB = initAllStubs.contains("GL_EXT_texture_sRGB");
        this.GL_EXT_texture_sRGB_decode = initAllStubs.contains("GL_EXT_texture_sRGB_decode");
        this.GL_EXT_texture_shared_exponent = initAllStubs.contains("GL_EXT_texture_shared_exponent");
        this.GL_EXT_texture_snorm = initAllStubs.contains("GL_EXT_texture_snorm");
        this.GL_EXT_texture_swizzle = initAllStubs.contains("GL_EXT_texture_swizzle");
        this.GL_EXT_timer_query = initAllStubs.contains("GL_EXT_timer_query");
        this.GL_EXT_transform_feedback = initAllStubs.contains("GL_EXT_transform_feedback");
        this.GL_EXT_vertex_array_bgra = initAllStubs.contains("GL_EXT_vertex_array_bgra");
        this.GL_EXT_vertex_attrib_64bit = initAllStubs.contains("GL_EXT_vertex_attrib_64bit");
        this.GL_EXT_vertex_shader = initAllStubs.contains("GL_EXT_vertex_shader");
        this.GL_EXT_vertex_weighting = initAllStubs.contains("GL_EXT_vertex_weighting");
        this.OpenGL11 = initAllStubs.contains("OpenGL11");
        this.OpenGL12 = initAllStubs.contains("OpenGL12");
        this.OpenGL13 = initAllStubs.contains("OpenGL13");
        this.OpenGL14 = initAllStubs.contains("OpenGL14");
        this.OpenGL15 = initAllStubs.contains("OpenGL15");
        this.OpenGL20 = initAllStubs.contains("OpenGL20");
        this.OpenGL21 = initAllStubs.contains("OpenGL21");
        this.OpenGL30 = initAllStubs.contains("OpenGL30");
        this.OpenGL31 = initAllStubs.contains("OpenGL31");
        this.OpenGL32 = initAllStubs.contains("OpenGL32");
        this.OpenGL33 = initAllStubs.contains("OpenGL33");
        this.OpenGL40 = initAllStubs.contains("OpenGL40");
        this.OpenGL41 = initAllStubs.contains("OpenGL41");
        this.OpenGL42 = initAllStubs.contains("OpenGL42");
        this.OpenGL43 = initAllStubs.contains("OpenGL43");
        this.OpenGL44 = initAllStubs.contains("OpenGL44");
        this.OpenGL45 = initAllStubs.contains("OpenGL45");
        this.GL_GREMEDY_frame_terminator = initAllStubs.contains("GL_GREMEDY_frame_terminator");
        this.GL_GREMEDY_string_marker = initAllStubs.contains("GL_GREMEDY_string_marker");
        this.GL_HP_occlusion_test = initAllStubs.contains("GL_HP_occlusion_test");
        this.GL_IBM_rasterpos_clip = initAllStubs.contains("GL_IBM_rasterpos_clip");
        this.GL_INTEL_map_texture = initAllStubs.contains("GL_INTEL_map_texture");
        this.GL_KHR_context_flush_control = initAllStubs.contains("GL_KHR_context_flush_control");
        this.GL_KHR_debug = initAllStubs.contains("GL_KHR_debug");
        this.GL_KHR_robust_buffer_access_behavior = initAllStubs.contains("GL_KHR_robust_buffer_access_behavior");
        this.GL_KHR_robustness = initAllStubs.contains("GL_KHR_robustness");
        this.GL_KHR_texture_compression_astc_ldr = initAllStubs.contains("GL_KHR_texture_compression_astc_ldr");
        this.GL_NVX_gpu_memory_info = initAllStubs.contains("GL_NVX_gpu_memory_info");
        this.GL_NV_bindless_multi_draw_indirect = initAllStubs.contains("GL_NV_bindless_multi_draw_indirect");
        this.GL_NV_bindless_texture = initAllStubs.contains("GL_NV_bindless_texture");
        this.GL_NV_blend_equation_advanced = initAllStubs.contains("GL_NV_blend_equation_advanced");
        this.GL_NV_blend_square = initAllStubs.contains("GL_NV_blend_square");
        this.GL_NV_compute_program5 = initAllStubs.contains("GL_NV_compute_program5");
        this.GL_NV_conditional_render = initAllStubs.contains("GL_NV_conditional_render");
        this.GL_NV_copy_depth_to_color = initAllStubs.contains("GL_NV_copy_depth_to_color");
        this.GL_NV_copy_image = initAllStubs.contains("GL_NV_copy_image");
        this.GL_NV_deep_texture3D = initAllStubs.contains("GL_NV_deep_texture3D");
        this.GL_NV_depth_buffer_float = initAllStubs.contains("GL_NV_depth_buffer_float");
        this.GL_NV_depth_clamp = initAllStubs.contains("GL_NV_depth_clamp");
        this.GL_NV_draw_texture = initAllStubs.contains("GL_NV_draw_texture");
        this.GL_NV_evaluators = initAllStubs.contains("GL_NV_evaluators");
        this.GL_NV_explicit_multisample = initAllStubs.contains("GL_NV_explicit_multisample");
        this.GL_NV_fence = initAllStubs.contains("GL_NV_fence");
        this.GL_NV_float_buffer = initAllStubs.contains("GL_NV_float_buffer");
        this.GL_NV_fog_distance = initAllStubs.contains("GL_NV_fog_distance");
        this.GL_NV_fragment_program = (initAllStubs.contains("GL_NV_fragment_program") && initAllStubs.contains("GL_NV_program"));
        this.GL_NV_fragment_program2 = initAllStubs.contains("GL_NV_fragment_program2");
        this.GL_NV_fragment_program4 = initAllStubs.contains("GL_NV_fragment_program4");
        this.GL_NV_fragment_program_option = initAllStubs.contains("GL_NV_fragment_program_option");
        this.GL_NV_framebuffer_multisample_coverage = initAllStubs.contains("GL_NV_framebuffer_multisample_coverage");
        this.GL_NV_geometry_program4 = initAllStubs.contains("GL_NV_geometry_program4");
        this.GL_NV_geometry_shader4 = initAllStubs.contains("GL_NV_geometry_shader4");
        this.GL_NV_gpu_program4 = initAllStubs.contains("GL_NV_gpu_program4");
        this.GL_NV_gpu_program5 = initAllStubs.contains("GL_NV_gpu_program5");
        this.GL_NV_gpu_program5_mem_extended = initAllStubs.contains("GL_NV_gpu_program5_mem_extended");
        this.GL_NV_gpu_shader5 = initAllStubs.contains("GL_NV_gpu_shader5");
        this.GL_NV_half_float = initAllStubs.contains("GL_NV_half_float");
        this.GL_NV_light_max_exponent = initAllStubs.contains("GL_NV_light_max_exponent");
        this.GL_NV_multisample_coverage = initAllStubs.contains("GL_NV_multisample_coverage");
        this.GL_NV_multisample_filter_hint = initAllStubs.contains("GL_NV_multisample_filter_hint");
        this.GL_NV_occlusion_query = initAllStubs.contains("GL_NV_occlusion_query");
        this.GL_NV_packed_depth_stencil = initAllStubs.contains("GL_NV_packed_depth_stencil");
        this.GL_NV_parameter_buffer_object = initAllStubs.contains("GL_NV_parameter_buffer_object");
        this.GL_NV_parameter_buffer_object2 = initAllStubs.contains("GL_NV_parameter_buffer_object2");
        this.GL_NV_path_rendering = initAllStubs.contains("GL_NV_path_rendering");
        this.GL_NV_pixel_data_range = initAllStubs.contains("GL_NV_pixel_data_range");
        this.GL_NV_point_sprite = initAllStubs.contains("GL_NV_point_sprite");
        this.GL_NV_present_video = initAllStubs.contains("GL_NV_present_video");
        this.GL_NV_primitive_restart = initAllStubs.contains("GL_NV_primitive_restart");
        this.GL_NV_register_combiners = initAllStubs.contains("GL_NV_register_combiners");
        this.GL_NV_register_combiners2 = initAllStubs.contains("GL_NV_register_combiners2");
        this.GL_NV_shader_atomic_counters = initAllStubs.contains("GL_NV_shader_atomic_counters");
        this.GL_NV_shader_atomic_float = initAllStubs.contains("GL_NV_shader_atomic_float");
        this.GL_NV_shader_buffer_load = initAllStubs.contains("GL_NV_shader_buffer_load");
        this.GL_NV_shader_buffer_store = initAllStubs.contains("GL_NV_shader_buffer_store");
        this.GL_NV_shader_storage_buffer_object = initAllStubs.contains("GL_NV_shader_storage_buffer_object");
        this.GL_NV_tessellation_program5 = initAllStubs.contains("GL_NV_tessellation_program5");
        this.GL_NV_texgen_reflection = initAllStubs.contains("GL_NV_texgen_reflection");
        this.GL_NV_texture_barrier = initAllStubs.contains("GL_NV_texture_barrier");
        this.GL_NV_texture_compression_vtc = initAllStubs.contains("GL_NV_texture_compression_vtc");
        this.GL_NV_texture_env_combine4 = initAllStubs.contains("GL_NV_texture_env_combine4");
        this.GL_NV_texture_expand_normal = initAllStubs.contains("GL_NV_texture_expand_normal");
        this.GL_NV_texture_multisample = initAllStubs.contains("GL_NV_texture_multisample");
        this.GL_NV_texture_rectangle = initAllStubs.contains("GL_NV_texture_rectangle");
        this.GL_NV_texture_shader = initAllStubs.contains("GL_NV_texture_shader");
        this.GL_NV_texture_shader2 = initAllStubs.contains("GL_NV_texture_shader2");
        this.GL_NV_texture_shader3 = initAllStubs.contains("GL_NV_texture_shader3");
        this.GL_NV_transform_feedback = initAllStubs.contains("GL_NV_transform_feedback");
        this.GL_NV_transform_feedback2 = initAllStubs.contains("GL_NV_transform_feedback2");
        this.GL_NV_vertex_array_range = initAllStubs.contains("GL_NV_vertex_array_range");
        this.GL_NV_vertex_array_range2 = initAllStubs.contains("GL_NV_vertex_array_range2");
        this.GL_NV_vertex_attrib_integer_64bit = initAllStubs.contains("GL_NV_vertex_attrib_integer_64bit");
        this.GL_NV_vertex_buffer_unified_memory = initAllStubs.contains("GL_NV_vertex_buffer_unified_memory");
        this.GL_NV_vertex_program = (initAllStubs.contains("GL_NV_vertex_program") && initAllStubs.contains("GL_NV_program"));
        this.GL_NV_vertex_program1_1 = initAllStubs.contains("GL_NV_vertex_program1_1");
        this.GL_NV_vertex_program2 = initAllStubs.contains("GL_NV_vertex_program2");
        this.GL_NV_vertex_program2_option = initAllStubs.contains("GL_NV_vertex_program2_option");
        this.GL_NV_vertex_program3 = initAllStubs.contains("GL_NV_vertex_program3");
        this.GL_NV_vertex_program4 = initAllStubs.contains("GL_NV_vertex_program4");
        this.GL_NV_video_capture = initAllStubs.contains("GL_NV_video_capture");
        this.GL_SGIS_generate_mipmap = initAllStubs.contains("GL_SGIS_generate_mipmap");
        this.GL_SGIS_texture_lod = initAllStubs.contains("GL_SGIS_texture_lod");
        this.GL_SUN_slice_accum = initAllStubs.contains("GL_SUN_slice_accum");
        this.tracker.init();
    }
}
