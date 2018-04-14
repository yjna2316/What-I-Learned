/**
 * 20. Valid Parentheses
 * https://leetcode.com/problems/valid-parentheses/#/description
 */

/* using stack */
public class Solution {
    public boolean isValid(String str) {
        if (str.length() % 2 == 1) return false;
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < str.length(); i ++) {
            char c = str.charAt(i);
            if (c == '(') stack.push(')');
            else if (c == '[') stack.push(']');
            else if (c == '{') stack.push('}');
            else if (stack.isEmpty() || stack.pop() != c) return false;
        }
        return stack.isEmpty();
    }
}

/* using stack with switch */
public class Solution {
  public boolean isValid(String str) {
      if (str.length() % 2 == 1) return false;
      Stack<Character> stack = new Stack<Character>();
      for (char c : str.toCharArray()) {
          switch (c) {
            case '(':
              stack.push(')');
              break;
            case '{':
              stack.push('}');
              break;
            case '[':
              stack.push(']');
              break;
            case ')':
            case ']':
            case '}':      
              if (stack.isEmpty() || stack.pop() != c) return false;
              break;
          }
      }    
      return stack.isEmpty();
  }
}

/* using HashMap*/
public class Solution {
  /** 
  private static final Map<Character, Character> map = new HashMap<Character, Character>() {{
      put('(', ')');
      put('{', '}');
      put('[', ']');
  }};
  */
  public boolean isValid(String s) {
    Map<Character, Character> openerToCloser = new HashMap<>();
    openerToCloser.put('(', ')');
    openerToCloser.put('[', ']');
    openerToCloser.put('{', '}');

    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
      if (openerToCloser.containsKey(c)) {
        stack.push(c);
      } else if (openerToCloser.containsValue(c)) {
        if (stack.isEmpty() || openerToCloser.get(stack.pop()) != c) {
          return false;
        }
      }
    }
    return stack.isEmpty();
  }

