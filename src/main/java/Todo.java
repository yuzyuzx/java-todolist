public class Todo {
  private String id;
  private String content;

  public Todo(String id, String content) {
    this.id = id;
    this.content = content;
  }
  
  public String getId() {
    return id;
  }
  
  public String getContent() {
    return content;
  }
}
