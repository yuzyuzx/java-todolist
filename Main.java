import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String input = "";
    
    boolean isLoop = true;
    if(input.equalsIgnoreCase("q")) {
      isLoop = false;
    }


    while(isLoop) {
      System.out.println("操作番号を選択してください");
      System.out.println("1: 表示");
      System.out.println("2: 作成");
      System.out.println("3: 削除");
      System.out.println("q: 終了");
      
      input = sc.nextLine();
      
      switch(input) {
        case "1":
          System.out.println("一覧表示");
          break;
          
        case "2":
          System.out.println("新規作成");
          break;
        
        case "3":
          System.out.println("削除");
          break;
        
        default:
          System.out.println("アプリを終了します\n");
          break;
      }
      

      if(input.equalsIgnoreCase("q")) {
        isLoop = false;
      }

      
      System.out.println("\n");
    }
    
    
    sc.close();
  }
  
}
