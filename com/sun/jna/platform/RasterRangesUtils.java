package com.sun.jna.platform;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class RasterRangesUtils
{
    private static final int[] subColMasks;
    private static final Comparator COMPARATOR;
    
    public static boolean outputOccupiedRanges(final Raster raster, final RangesOutput rangesOutput) {
        final Rectangle bounds = raster.getBounds();
        final SampleModel sampleModel = raster.getSampleModel();
        final boolean b = sampleModel.getNumBands() == 4;
        if (raster.getParent() == null && bounds.x == 0 && bounds.y == 0) {
            final DataBuffer dataBuffer = raster.getDataBuffer();
            if (dataBuffer.getNumBanks() == 1) {
                if (sampleModel instanceof MultiPixelPackedSampleModel) {
                    if (((MultiPixelPackedSampleModel)sampleModel).getPixelBitStride() == 1) {
                        return outputOccupiedRangesOfBinaryPixels(((DataBufferByte)dataBuffer).getData(), bounds.width, bounds.height, rangesOutput);
                    }
                }
                else if (sampleModel instanceof SinglePixelPackedSampleModel && sampleModel.getDataType() == 3) {
                    return outputOccupiedRanges(((DataBufferInt)dataBuffer).getData(), bounds.width, bounds.height, b ? -16777216 : 16777215, rangesOutput);
                }
            }
        }
        return outputOccupiedRanges(raster.getPixels(0, 0, bounds.width, bounds.height, (int[])null), bounds.width, bounds.height, b ? -16777216 : 16777215, rangesOutput);
    }
    
    public static boolean outputOccupiedRangesOfBinaryPixels(final byte[] array, final int n, final int n2, final RangesOutput rangesOutput) {
        final HashSet<Rectangle> set = new HashSet<Rectangle>();
        Set<Rectangle> empty_SET = (Set<Rectangle>)Collections.EMPTY_SET;
        final int n3 = array.length / n2;
        for (int i = 0; i < n2; ++i) {
            final TreeSet<Rectangle> set2 = new TreeSet<Rectangle>(RasterRangesUtils.COMPARATOR);
            final int n4 = i * n3;
            int n5 = -1;
            for (int j = 0; j < n3; ++j) {
                final int n6 = j << 3;
                final byte b = array[n4 + j];
                if (b == 0) {
                    if (n5 >= 0) {
                        set2.add(new Rectangle(n5, i, n6 - n5, 1));
                        n5 = -1;
                    }
                }
                else if (b == 255) {
                    if (n5 < 0) {
                        n5 = n6;
                    }
                }
                else {
                    for (int k = 0; k < 8; ++k) {
                        final int n7 = n6 | k;
                        if ((b & RasterRangesUtils.subColMasks[k]) != 0x0) {
                            if (n5 < 0) {
                                n5 = n7;
                            }
                        }
                        else if (n5 >= 0) {
                            set2.add(new Rectangle(n5, i, n7 - n5, 1));
                            n5 = -1;
                        }
                    }
                }
            }
            if (n5 >= 0) {
                set2.add(new Rectangle(n5, i, n - n5, 1));
            }
            set.addAll((Collection<?>)mergeRects(empty_SET, set2));
            empty_SET = set2;
        }
        set.addAll((Collection<?>)empty_SET);
        for (final Rectangle rectangle : set) {
            if (!rangesOutput.outputRange(rectangle.x, rectangle.y, rectangle.width, rectangle.height)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean outputOccupiedRanges(final int[] array, final int n, final int n2, final int n3, final RangesOutput rangesOutput) {
        final HashSet<Rectangle> set = new HashSet<Rectangle>();
        Set<Rectangle> empty_SET = (Set<Rectangle>)Collections.EMPTY_SET;
        for (int i = 0; i < n2; ++i) {
            final TreeSet<Rectangle> set2 = new TreeSet<Rectangle>(RasterRangesUtils.COMPARATOR);
            final int n4 = i * n;
            int n5 = -1;
            for (int j = 0; j < n; ++j) {
                if ((array[n4 + j] & n3) != 0x0) {
                    if (n5 < 0) {
                        n5 = j;
                    }
                }
                else if (n5 >= 0) {
                    set2.add(new Rectangle(n5, i, j - n5, 1));
                    n5 = -1;
                }
            }
            if (n5 >= 0) {
                set2.add(new Rectangle(n5, i, n - n5, 1));
            }
            set.addAll((Collection<?>)mergeRects(empty_SET, set2));
            empty_SET = set2;
        }
        set.addAll((Collection<?>)empty_SET);
        for (final Rectangle rectangle : set) {
            if (!rangesOutput.outputRange(rectangle.x, rectangle.y, rectangle.width, rectangle.height)) {
                return false;
            }
        }
        return true;
    }
    
    private static Set mergeRects(final Set set, final Set set2) {
        final HashSet set3 = new HashSet(set);
        if (!set.isEmpty() && !set2.isEmpty()) {
            final Rectangle[] array = set.toArray(new Rectangle[set.size()]);
            final Rectangle[] array2 = set2.toArray(new Rectangle[set2.size()]);
            int n = 0;
            int n2 = 0;
            while (n < array.length && n2 < array2.length) {
                while (array2[n2].x < array[n].x) {
                    if (++n2 == array2.length) {
                        return set3;
                    }
                }
                if (array2[n2].x == array[n].x && array2[n2].width == array[n].width) {
                    set3.remove(array[n]);
                    array2[n2].y = array[n].y;
                    array2[n2].height = array[n].height + 1;
                    ++n2;
                }
                else {
                    ++n;
                }
            }
        }
        return set3;
    }
    
    static {
        subColMasks = new int[] { 128, 64, 32, 16, 8, 4, 2, 1 };
        COMPARATOR = new Comparator() {
            public int compare(final Object o, final Object o2) {
                return ((Rectangle)o).x - ((Rectangle)o2).x;
            }
        };
    }
    
    public interface RangesOutput
    {
        boolean outputRange(final int p0, final int p1, final int p2, final int p3);
    }
}
