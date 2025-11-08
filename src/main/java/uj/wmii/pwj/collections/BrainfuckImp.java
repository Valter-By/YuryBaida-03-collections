package uj.wmii.pwj.collections;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BrainfuckImp implements Brainfuck {

    private final String program;
    private final PrintStream out;
    private final InputStream in;
    private final byte[] data;
    private int pointer;
    private final Map<Integer, Integer> loopMap;


    public BrainfuckImp(String program, PrintStream out, InputStream in, int stackSize) {
        this.program = program;
        this.out = out;
        this.in = in;
        this.data = new byte[stackSize];
        this.pointer = 0;
        this.loopMap = countLoops();
    }

    private Map<Integer, Integer> countLoops() {
        Map<Integer, Integer> ans = new HashMap<Integer, Integer>();
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < program.length(); i++) {
            if (program.charAt(i) == '[') {
                stack.push(i);
            } else if (program.charAt(i) == ']') {
                int key = stack.pop();
                int value = i;
                ans.put(key, value);
                ans.put(value, key);
            }
        }
        return ans;
    }

    @Override
    public void execute() {
        int cnt = 0;
        while (cnt < program.length()) {
            char command = program.charAt(cnt);
            switch (command) {
                case '>':
                    pointer++;
                    break;
                case '<':
                    pointer--;
                    break;
                case '+':
                    data[pointer]++;
                    break;
                case '-':
                    data[pointer]--;
                    break;
                case '.':
                    out.print((char) data[pointer]);
                    break;
                case ',':
                    try {
                        data[pointer] = (byte) in.read();
                    } catch (Exception e) {
                        throw new RuntimeException("Input error", e);
                    }                    
                    break;
                case '[':
                    if (data[pointer] == 0) {
                        cnt = loopMap.get(cnt);
                    }
                    break;
                case ']':
                    if (data[pointer] != 0) {
                        cnt = loopMap.get(cnt);
                    }
                default:
                    break;
            }
            cnt++;
        }
    }
}
