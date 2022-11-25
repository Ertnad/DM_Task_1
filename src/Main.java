import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[][] mat = readIntArray2FromFile("input.txt");

        int n = mat != null ? mat.length : 0;

        saveResultInFile(mat, n);

    }

    static int determinantOfMatrix(int[][] mat, int n) {

        int D = 0;

        if (n == 1) { return mat[0][0]; }

        if (n == 2) { return mat[0][0] * mat[1][1] - mat[1][0] * mat[0][1]; }

        int[][] temp = new int[n][n];

        for (int f = 0; f < n; f++) {
            int i = 0, j = 0;
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (row != 0 && col != f) {
                        temp[i][j++] = mat[row][col];
                        if (j == n - 1) {
                            j = 0;
                            i++;
                        }
                    }
                }
            }
            D += Math.pow(-1, f) * mat[0][f] * determinantOfMatrix(temp, n - 1);
        }

        return D;
    }

    // Ниже расположены методы, чтобы считать матрицу из файла

    public static int[][] readIntArray2FromFile(String fileName) {
        try {
            return toIntArray2(readLinesFromFile(fileName));
        }
        catch(IOException e) {
            return null;
        }
    }

    public static int[][] toIntArray2(String[] lines) {
        int[][] arr2 = new int[lines.length][];
        for (int r = 0; r < lines.length; r++) {
            arr2[r] = toIntArray(lines[r]);
        }
        return arr2;
    }

    public static int[] toIntArray(String str) {
        Scanner scanner = new Scanner(str);
        scanner.useDelimiter("(\\s|[,;])+");
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.nextInt());
        }

        Integer[] arr = list.toArray(new Integer[0]);
        return toPrimitive(arr);
    }

    public static int[] toPrimitive(Integer[] arr) {
        if (arr == null) {
            return null;
        }
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    public static String[] readLinesFromFile(String fileName) throws IOException {
        List<String> lines;
        try (Scanner scanner = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            lines = new ArrayList<>();
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines.toArray(new String[0]);
    }

    // А этот метод выводит результат в файл
    public static void saveResultInFile(int[][] mat, int n) {
        PrintWriter writer;
        try {
            writer = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.println(determinantOfMatrix(mat, n));
        writer.close();
    }

}