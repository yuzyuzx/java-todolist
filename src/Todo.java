public class Todo {
  private final String id;
  private final String title;

  public Todo(String id, String title) {
    this.id = id;
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }
}