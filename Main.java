import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.File;

public class Main {
  
  public static final String TODO_CSV_FILE = "./data/todo.csv";
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String input = "";
    
    boolean isLoop = true;
    if(input.equalsIgnoreCase("4")) {
      isLoop = false;
    }


    while(isLoop) {
      
      showOperation();
      input = sc.nextLine();
      
      switch(input) {
        case "1":
          System.out.println("一覧表示");
          showTodo();
          break;
          
        case "2":
          System.out.println("新規作成");
          break;
        
        case "3":
          System.out.println("削除");
          break;
        
        default:
          System.out.println("アプリを終了します\n");
          isLoop = false;
          break;
      }
      
      
      System.out.println("\n");
    }
    
    
    sc.close();
  }
  
  private static void showOperation() {
    System.out.println("操作番号を選択してください");
    System.out.println("1: 表示");
    System.out.println("2: 作成");
    System.out.println("3: 削除");
    System.out.println("4: 終了");
  }
  
  private static void showTodo() {

    try(Scanner sc = new Scanner(new File(TODO_CSV_FILE))) {
      while(sc.hasNextLine()) {
        String line = sc.nextLine();
        System.out.println("line: " + line);
        // StringTokenizer st = new StringTokenizer(line, ",");
        // while(st.hasMoreTokens()) {
        //   String t = st.nextToken();
        //   System.out.println(t);
        // }
      }
    } catch(IOException e) {
      System.out.println("指定されたファイルが見つかりません");
      // e.printStackTrace();
    }
  }
  
  
  
}
