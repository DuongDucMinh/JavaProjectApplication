package com.example.javafxapp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Thông tin sách mẫu
        String id = "001";
        String title = "Lập trình Mẫu Giấo";
        String author = "Bùi Xuân Huấn";
        int year = 2024;
        String summary = "Cuốn sách hướng dẫn cơ bản về lập trình Javalorant và Htmlol.";

        // Tạo file HTML
        //HtmlGenerator.generateHtml(id, title, author, year, summary);

        // Tạo QR Code trỏ đến trang HTML, lưu vào qrcode
        //QRCodeGenerator.generateQRCode(id, 300, 300);

    }
}
