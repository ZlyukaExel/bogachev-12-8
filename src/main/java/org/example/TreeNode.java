package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeNode {
    private final String value;
    private final List<TreeNode> children;

    public TreeNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public static TreeNode makeTree(String input) {
        //убираем лишние пробелы и скобки по бокам
        input = input.replace(" ", "");
        input = input.substring(1, input.length() - 1);

        //если элемент один, то проходить всю программу нет смысла
        if (input.contains(",")) {
            String[] allElements = input.split(",");

            //сохраняем корень дерева
            TreeNode result = new TreeNode(allElements[0]);
            String[] elements = Arrays.copyOfRange(allElements, 1, allElements.length);

            //значение узла
            StringBuilder val = new StringBuilder();
            int bracketCounter = 0;

            //проходим по каждому элементу
            for (String element : elements) {
                //если это узел, добавляем всё подряд
                if (element.contains("(") || bracketCounter > 0) {
                    for (char ch : element.toCharArray()) {
                        val.append(ch);
                        if (ch == '(')
                            bracketCounter++;
                        if (ch == ')')
                            bracketCounter--;
                        if (bracketCounter == 0) {
                            result.children.add(makeTree(val.toString()));
                            val.setLength(0);
                        }
                    }
                    if (!val.isEmpty())
                        val.append(',');
                } else {
                    result.children.add(new TreeNode(element));
                }
            }
            return result;
        } else
            return new TreeNode(input);
    }

    public static StringBuilder prefix = new StringBuilder();

    public static void PrintTree(TreeNode tree) {
        System.out.println(tree.value);
        for (int i = 0; i < tree.children.size(); i++) {
            prefix.setLength(0);
            PrintChildren(tree.children.get(i), i == tree.children.size() - 1);
        }
    }

    public static void PrintChildren(TreeNode tree, boolean isLast) {
        System.out.println(prefix.toString() + "+-" + tree.value);
        if (isLast)
            prefix.append("  ");
        else
            prefix.append("| ");
        for (int i = 0; i < tree.children.size(); i++) {
            PrintChildren(tree.children.get(i), i == tree.children.size() - 1);
        }
        prefix.setLength(prefix.length() - 2);
    }

    public static void main(String[] args) {
        String input1 = "(a, b, (c), d)";
        TreeNode root1 = makeTree(input1);
        PrintTree(root1);

        System.out.println("\n");

        String input2 = "(a, second, (abc, y, (x, 7), uuu, (8, 9, (10, 1))), abcddcba)";
        TreeNode root2 = makeTree(input2);
        PrintTree(root2);

        System.out.println("\n");

        String input3 = "(a, (b, (c, (d, (e, (f, (g, (h, (i, (j, (k, (l, (m, (n, (o, (p)))))), x))))), y)))), z)";
        TreeNode root3 = makeTree(input3);
        PrintTree(root3);

        System.out.println("\n");

        String input4 = "(a, (b, (c, (d, (e, (f, (g, (h, (i, (j, (k, (l, (m, (n, (o, (p)))))), x))))), y)))))";
        TreeNode root4 = makeTree(input4);
        PrintTree(root4);
    }
}
