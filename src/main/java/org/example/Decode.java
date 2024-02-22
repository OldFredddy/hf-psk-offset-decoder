package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Decode {


    public static String processFile(Path path) {
        String inputFileName = path.toString();
        String outputFileName = inputFileName.replace(".txt", "_output.txt");
        try {
            String content = new String(Files.readAllBytes(path));
            String digits = content.replaceAll("\\D+", "");
            if (digits.isEmpty()) {
                String s = "Файл " + inputFileName + " не содержит цифр.";
                System.out.println(s);
                return s;
            }

            int maxDigit = digits.chars().max().getAsInt() - '0';
            int base = determineBase(maxDigit);
            String binaryString = convertToBinaryString(digits, base);
            String decodedBinaryString = relativeDecode(binaryString);
            String result = convertFromBinaryString(decodedBinaryString, base);

            Path outputPath = Paths.get(outputFileName);
            Files.write(outputPath, result.getBytes());
            String s =  "Результат обработки " + inputFileName + " записан в файл: " + outputFileName;
            System.out.println(s);
            return s;
        } catch (IOException e) {
            String s ="Ошибка при работе с файлом " + inputFileName + ": " + e.getMessage();
                    System.err.println(s);
                    return s;
        }
    }
    private static int determineBase(int maxDigit) {
        if (maxDigit < 2) return 2;
        if (maxDigit < 4) return 4;
        return 8;
    }

    private static String convertToBinaryString(String digits, int base) {
        StringBuilder binaryString = new StringBuilder();
        for (char digit : digits.toCharArray()) {
            int value = Character.digit(digit, 10);
            String binary = Integer.toBinaryString(value);
            // Дополняем нулями до длины, соответствующей базе
            while (binary.length() < Integer.toBinaryString(base - 1).length()) {
                binary = "0" + binary;
            }
            binaryString.append(binary);
        }
        return binaryString.toString();
    }

    private static String relativeDecode(String binaryString) {
        StringBuilder decoded = new StringBuilder();
        char previousBit = '0'; // Начальное значение для относительного декодирования
        for (int i = 0; i < binaryString.length(); i++) {
            char currentBit = binaryString.charAt(i);
            // Сложение по модулю два предыдущего декодированного бита и текущего бита
            char decodedBit = (char)(((currentBit - '0') + (previousBit - '0')) % 2 + '0');
            decoded.append(decodedBit);
            previousBit = decodedBit; // Обновляем предыдущий бит на основе результата сложения
        }
        return decoded.toString();
    }

    private static String convertFromBinaryString(String binaryString, int base) {
        StringBuilder result = new StringBuilder();
        int bitsPerDigit = Integer.toBinaryString(base - 1).length();
        for (int i = 0; i < binaryString.length(); i += bitsPerDigit) {
            String bitSubset = binaryString.substring(i, Math.min(i + bitsPerDigit, binaryString.length()));
            int decimal = Integer.parseInt(bitSubset, 2);
            result.append(decimal);
        }
        return result.toString();
    }
}
