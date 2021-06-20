package com.tremollo.api.Utils;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoHelper {
    public static List<File> grabFrames(File sourceFile,int framesToCapture) throws FrameGrabber.Exception {
        long bench = System.currentTimeMillis();
        System.out.println("grabbing frames from : " + sourceFile.getAbsolutePath());
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(sourceFile.getAbsolutePath());
        ff.start();
        String rotate = ff.getVideoMetadata("rotate");
        int ffLength = ff.getLengthInFrames();
        List<Integer> randomGrab = random(ffLength, framesToCapture);
        List<File> generatedFrames = new ArrayList<>();
        Java2DFrameConverter frameConverter = new Java2DFrameConverter();
        int maxRandomGrab = randomGrab.get(randomGrab.size() - 1);
        Frame f;
        int i = 0;
        while (i < ffLength) {
            f = ff.grabImage();
            if (randomGrab.contains(i)) {
                if (null != rotate && rotate.length() > 1) {
                    OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
                    opencv_core.IplImage src = converter.convert(f);
                    f = converter.convert(rotate(src, Integer.valueOf(rotate)));
                }

                //converting images to file
                String outputFileName = sourceFile.getName()+"_"+i+".png";
                String outputFile = sourceFile.getAbsolutePath().replace(sourceFile.getName(),outputFileName);
                BufferedImage bi = frameConverter.getBufferedImage(f);
                File output = new File(outputFile);
                try {
                    ImageIO.write(bi, "png", output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                generatedFrames.add(output);
            }
            if (i >= maxRandomGrab) {
                break;
            }
            i++;
        }
        ff.stop();
        bench = System.currentTimeMillis()-bench;
        System.out.println("Execution time : "+bench/1000.0f+" s");
        return generatedFrames;
    }

    public static List<Integer> random(int baseNum, int length) {
        List<Integer> list = new ArrayList<Integer>(length);
        int block = baseNum/(length*4);
        for(int i=1;i<=length;i++)
            list.add(block*i);
        return list;
    }

    public static opencv_core.IplImage rotate(opencv_core.IplImage src, int angle) {
        opencv_core.IplImage img = opencv_core.IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
        opencv_core.cvTranspose(src, img);
        opencv_core.cvFlip(img, img, angle);
        return img;
    }
}
