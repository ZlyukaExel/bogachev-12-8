package org.example;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String value;
    private List<TreeNode> children;
    private static StringBuilder prefix = new StringBuilder();

    public TreeNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public static TreeNode parseTree(String input) {
        input = input.replace(" ", "");

        if (input.contains(",")) {
            for (int i = 0; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch != ',')
                    System.out.print(ch);
                else {
                    System.out.print("\n" + prefix.toString() + "+-");
                    input = input.substring(i + 1);
                    break;
                }
            }
        } else {
            prefix.setLength(prefix.length() - 2);
            System.out.print(input);
            return null;
        }
        return parseSubtree(input);
    }

    private static TreeNode parseSubtree(String input) {
        StringBuilder newNode = new StringBuilder();
        int bracketCounter = 0;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            //если это узел, добавляем всё подряд
            if (bracketCounter > 0) {
                //каждая ) понижает счётчик
                if (ch == ')') {
                    bracketCounter--;
                    //если счётчик обнулился, строим дерево по полученной строке
                    if (bracketCounter == 0) {
                        //проверяем, есть ли после узла ещё элементы
                        if (i + 1 < input.length() && input.charAt(i + 1) == ',')
                            prefix.append("| ");
                        else
                            prefix.append("  ");
                        parseTree(newNode.toString());
                        newNode.setLength(0);
                    } else
                        newNode.append(ch); //сохраняем ) если узел не закончился
                } else {
                    //каждая ( повышает счётчик
                    if (ch == '(')
                        bracketCounter++;
                    //сохраняем все остальные символы
                    newNode.append(ch);
                }
            } else if (ch == ',') { //если началось новое слово, переходим к новой строке
                System.out.print("\n" + prefix.toString() + "+-");
            } else if (ch == '(') { //если начался узел, повышаем счётчик
                bracketCounter++;
            } else //если это обычный символ, выводим его на экран
                System.out.print(ch);
        }

        if (!prefix.isEmpty())
            prefix.setLength(prefix.length() - 2);

        return null;
    }

    public static boolean isLast(String substring) {
        return true;
    }

    public static void main(String[] args) {
        String input1 = "(a, b, (c), d)";
        TreeNode root1 = parseTree(input1.substring(1, input1.length() - 1));

        System.out.println();
        System.out.println();

        prefix.setLength(0);
        String input2 = "(a, second, (abc, y, (x, 7), uuu, (8, 9, (10, 1))), abcddcba)";
        TreeNode root2 = parseTree(input2.substring(1, input2.length() - 1)); //без скобок
    }
}
