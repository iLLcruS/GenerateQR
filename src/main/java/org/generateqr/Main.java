package org.generateqr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите данные для QR-кода: ");
        String inputData = scanner.nextLine();

        System.out.print("Введите ширину QR-кода: ");
        int width = scanner.nextInt();

        System.out.print("Введите высоту QR-кода: ");
        int height = scanner.nextInt();

        try {
            File qrCodeFile = generateQRCode(inputData, width, height);
            System.out.println("QR Code успешно создан и сохранен в " + qrCodeFile.getAbsolutePath());

            openImage(qrCodeFile);
        } catch (WriterException | IOException e) {
            System.out.println("Ошибка при создании QR Code: " + e.getMessage());
        }
    }

    private static File generateQRCode(String data, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        File qrCodeFile = new File("qrcode.png");
        ImageIO.write(image, "png", qrCodeFile);
        return qrCodeFile;
    }

    private static void openImage(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Не удалось открыть изображение. Пожалуйста, откройте файл вручную: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при открытии изображения: " + e.getMessage());
        }
    }
}