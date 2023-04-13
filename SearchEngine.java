import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> content = new ArrayList<>();
    

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Current List: %s", content);
        } 
        else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    content.add(parameters[1]);
                    return String.format("Add %s to list! It's now %s", parameters[1], content);
                }
        }
        else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
            ArrayList<String> matches = new ArrayList<>();
                if (parameters[0].equals("s")) {
                    for(String elem: content)
                        if (elem.contains(parameters[1]))
                            matches.add(elem);
                    return String.format("%s contain %s!", matches, parameters[1]);
                }
        }
        return "Error 404";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
