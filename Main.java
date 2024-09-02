import java.util.*;
import java.io.*;

public class Main {
  
  public static final String TODO_CSV_FILE = "./data/todo.csv";

  ArrayList<Todo> todolist = new ArrayList<>();

  public static void main(String[] args) {
    
    Main mainIns = new Main();
    
    mainIns.storeTodoData();
    // mainIns.show();
    // mainIns.post();
    mainIns.delete();
    
    /*
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
          show();
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
    */
  }
  
  private static void showOperation() {
    System.out.println("操作番号を選択してください");
    System.out.println("1: 表示");
    System.out.println("2: 作成");
    System.out.println("3: 削除");
    System.out.println("4: 終了");
  }
  
  private void storeTodoData() {
    try(Scanner sc = new Scanner(new File(TODO_CSV_FILE))) {
      while(sc.hasNextLine()) {
        String line = sc.nextLine();
        
        String[] fields = line.split(",");
        if(fields.length != 2) {
          continue;
        }
        
        Todo todo = new Todo(fields[0], fields[1]);
        todolist.add(todo);
      } // end while
    } catch(IOException e) {
      System.out.println("指定されたファイルが見つかりません");
    }
  }
  
  
  
  private void show() {
    // TODO 表示方法変更する
    for(Todo todo : todolist) {
      System.out.println(todo.getId() + "," + todo.getContent());
    }
  }
  
  private void post() {
    
    // 最新のIDを作成する
    // TODO メソッド化
    String newlastId = "1";
    if(!todolist.isEmpty()) {
      String currentLastId = todolist.get(todolist.size() - 1).getId();
      try {
        int number = Integer.parseInt(currentLastId) + 1;
        newlastId = String.valueOf(number);
      } catch(NumberFormatException e) {
        System.out.println("IDの変換処理に失敗しました");
      }
    }
    

    // 標準入力からデータを取得する
    // TODO ファイル更新前にバックアップファイルを作る
    try(Scanner sc = new Scanner(System.in)) {
      String content = sc.nextLine();
      
      // IDと入力データをカンマ区切りでファイルに書き込む
      String writeContent = String.join(",", newlastId, content);

      try(BufferedWriter bf = new BufferedWriter(
        new FileWriter(TODO_CSV_FILE, true)
      )) {
        bf.write(writeContent);
        bf.write("\n");
        System.out.println("入力内容を登録しました");
        
      // storeに新規データを登録する
      todolist.add(new Todo(newlastId, content));

      } catch (IOException e) {
        System.out.println("登録に失敗しました");
      }
        
    }
  }    
  
  /**
   * 画面にファイルに記録しているデータを表示する
   * 削除する番号を入力する
   * 入力した番号を取得する
   * storeから一致するデータを取得する
   * storeから削除する
   * IDを振り直してstoreを更新する
   * ファイルに保存する
   */
  private void delete() {
    // TODO 標準入力受付
    
    System.out.println("削除する番号を入力してください");

    // 削除ID存在チェック
    // TODO メソッド化する
    String stringDeleteId = "3";
    try {
      int intDeleteId = Integer.parseInt(stringDeleteId) - 1;
      if(intDeleteId < 0 || todolist.size() <= intDeleteId) {
        throw new NumberFormatException();
      }
      todolist.remove(intDeleteId);
    } catch(NumberFormatException e) {
      System.out.println("入力した番号のデータは存在しません");
      return;
    }
    
    // storeを更新する
    ArrayList<Todo> tmpTodo = new ArrayList<>();
    for(int i = 0; i < todolist.size(); i++) {
      String id = String.valueOf(i + 1);
      String content = todolist.get(i).getContent();
      tmpTodo.add(new Todo(id, content));
    }

    todolist = tmpTodo;
    
    // TODO 更新前にバックアップファイルを作る
    // ファイルを空にする
    try(FileWriter fw = new FileWriter(TODO_CSV_FILE, false)) {
    } catch (IOException e) {
      System.out.println("データファイルの更新処理に失敗しました");
    }
    
    // データファイル更新処理
    for(Todo todo : todolist) {

      try(BufferedWriter bf = new BufferedWriter( new FileWriter(TODO_CSV_FILE, true))) {

        String writeContent = String.join(
          ",", todo.getId(), todo.getContent()
        );

        bf.write(writeContent);
        bf.write("\n");
        
      } catch (IOException e) {
        System.out.println("更新処理に失敗しました");
      }

    }
    
  }

}
