import java.util.*;

public class Main {

  public static void main(String[] args) {

    Operation operation = new Operation();
    TodoList todoList = new TodoList();

    try(Scanner sc = new Scanner(System.in)) {
      operation.startOperation();

      while(operation.isLoopActive()) {
        operation.showOperation();

        String input = sc.nextLine();

        switch(input) {
          case "1":
            todoList.showTodoList();
            break;

          case "2":
            todoList.post(sc);
            break;

          case "3":
            todoList.removeTodo(sc);
            break;

          default:
            operation.endOperation();
            System.out.println("アプリを終了します\n");
            break;
        }

        System.out.println("\n");
      }
    }
  }
}