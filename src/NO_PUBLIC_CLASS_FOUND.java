import java.util.*;

interface IDocument{
    List<String> getDocumentText();
}
class BasicDocument implements IDocument{
    List<String> text;

    public BasicDocument(List<String> text) {
        this.text = text;
    }

    @Override
    public List<String> getDocumentText() {
        return text;
    }
}
abstract class DocumentDecorator implements IDocument{
    protected IDocument document;

    public DocumentDecorator(IDocument document) {
        this.document = document;
    }

    @Override
    public List<String> getDocumentText() {
       return document.getDocumentText();
    }
}

class EnableLineDocument extends DocumentDecorator{

    public EnableLineDocument(IDocument document) {
        super(document);
    }
    @Override
    public List<String> getDocumentText() {
        List<String> original = super.getDocumentText();
        List<String> result = new ArrayList<>();

        for (int i = 0; i < original.size(); i++) {
            result.add((i + 1) + ": " + original.get(i));
        }
        return result;
    }
}
class EnableWordCountDocument extends DocumentDecorator{

    public EnableWordCountDocument(IDocument document) {
        super(document);
    }
    @Override
    public List<String> getDocumentText() {
        List<String> original = super.getDocumentText();
        List<String> result = new ArrayList<>(original);

        int sum = 0;
        for (String s : original) {
            sum += s.split("\\s+").length;
        }

        result.add("Words: " + sum);
        return result;
    }
}
class EnableRedactionDocument extends DocumentDecorator{
    List<String> forbiddenWords;
    public EnableRedactionDocument(IDocument document,List<String> forbiddenWords) {
        super(document);
        this.forbiddenWords=forbiddenWords;
    }
    @Override
    public List<String> getDocumentText() {
        List<String> original = super.getDocumentText();
        List<String> result = new ArrayList<>();

        for (String line : original) {
            String[] words = line.split(        "\\s+");

            for (int i = 0; i < words.length; i++) {
                if (forbiddenWords.contains(words[i].toLowerCase())) {
                    words[i] = "*";
                }
            }

            result.add(String.join(" ", words));
        }
        return result;
    }

}

 class DocumentViewer{
    Map<String,IDocument> documents;

    public DocumentViewer() {
        this.documents = new HashMap<>();
    }
    public void addDocument(String id, String text){
        List<String> lines = new ArrayList<>(Arrays.asList(text.split(",")));
        documents.putIfAbsent(id,new BasicDocument(lines));
    }
    public void enableLineNumbers(String id){
        IDocument doc= new EnableLineDocument(documents.get(id));
        documents.put(id,doc);
    }
    public void enableWordCount(String id){
        IDocument doc= new EnableWordCountDocument(documents.get(id));
        documents.put(id,doc);
    }
    public void enableRedaction(String id, List<String> forbiddenWords){
        IDocument doc= new EnableRedactionDocument(documents.get(id),forbiddenWords);
        documents.put(id,doc);
    }
    public void display(String id){
        System.out.println("=== Document "+id+" ===");
        for (String string : documents.get(id).getDocumentText()) {
            System.out.println(string);
        }
    }


}
public class NO_PUBLIC_CLASS_FOUND {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        DocumentViewer documentViewer = new DocumentViewer();

        // 1. Чита број на документи
        int numOfDocs = input.nextInt();
        input.nextLine(); // чита нов ред

        for (int i = 0; i < numOfDocs; i++) {
            String idOfDoc = input.nextLine(); // ID на документ
            int lines = input.nextInt();       // број на редови
            input.nextLine();                  // чита нов ред

            StringBuilder allLines = new StringBuilder();
            for (int j = 0; j < lines; j++) {
                allLines.append(input.nextLine());
                if (j != lines - 1) allLines.append(","); // одвојува редови со ","
            }

            documentViewer.addDocument(idOfDoc, allLines.toString());
        }

        // 2. Читање на команди додека не дојде exit
        while (true) {
            String commandLine = input.nextLine().trim();
            if (commandLine.equalsIgnoreCase("exit")) break;

            String[] parts = commandLine.split("\\s+");
            String command = parts[0];
            String id = parts[1];

            switch (command) {
                case "enableLineNumbers":
                    documentViewer.enableLineNumbers(id);
                    break;
                case "enableWordCount":
                    documentViewer.enableWordCount(id);
                    break;
                case "enableRedaction":
                    List<String> forbidden = new ArrayList<>();
                    for (int i = 2; i < parts.length; i++) {
                        forbidden.add(parts[i].toLowerCase());
                    }
                    documentViewer.enableRedaction(id, forbidden);
                    break;
                case "display":
                    documentViewer.display(id);
                    break;
                default:
                    System.out.println("Unknown command: " + commandLine);
            }
        }
    }
}

