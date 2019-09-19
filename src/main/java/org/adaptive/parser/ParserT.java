import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.options.Options;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static jdk.nashorn.api.scripting.ScriptUtils.parse;


public class ParserT {

    private static final Gson PRETTY_PRINT_GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Gson GSON = new Gson();


    public static String toJson(ParseTree tree, boolean prettyPrint) {
        return prettyPrint ? PRETTY_PRINT_GSON.toJson(toMap(tree)) : GSON.toJson(toMap(tree));
    }

    public static Map<String, Object> toMap(ParseTree tree) {
        Map<String, Object> map = new LinkedHashMap<>();
        traverse(tree, map);
        return map;
    }

    public static void traverse(ParseTree tree, Map<String, Object> map) {

        if (tree instanceof TerminalNodeImpl) {
            Token token = ((TerminalNodeImpl) tree).getSymbol();
            map.put("line", token.getLine());
            map.put("column", token.getCharPositionInLine());
            map.put("type",JavaScriptLexer.VOCABULARY.getSymbolicName(token.getType()));
            map.put("text", token.getText());
        }else {
            List<Map<String, Object>> children = new ArrayList<>();
            String name = tree.getClass().getSimpleName().replaceAll("Context$", "");
            map.put(Character.toLowerCase(name.charAt(0)) + name.substring(1), children);
//            RuleNode ruleNode = (RuleNode)tree;
//            RuleContext ruleContext = ruleNode.getRuleContext();
//            System.out.println(JavaScriptLexer.ruleNames[ruleContext.getRuleIndex()]);
            for (int i = 0; i < tree.getChildCount(); i++) {
                Map<String, Object> nested = new LinkedHashMap<>();
                children.add(nested);
                traverse(tree.getChild(i), nested);
            }
        }
    }



    public static void main(String[] args) throws ScriptException, JSONException {

        Options options = new Options("nashorn");
        options.set("anon.functions", true);
        options.set("parse.only", true);
        options.set("scripting", true);
        ErrorManager errors = new ErrorManager();
        Context contextm = new Context(options, errors, Thread.currentThread().getContextClassLoader());
        Context.setGlobal(contextm.createGlobal());

        String a = "function a () {\n" +
                "            var a=10;\n" +
                "            if(a>10){\n" +
                "                a=a+1;\n" +
                "            else{\n" +
                "                a=a-1;\n" +
                "            }\n" +
                "        }";

        try {
            String parseTree = parse(a, "nashorn", true);
            final JSONObject obj = new JSONObject(parseTree);
            final JSONArray geodata = obj.getJSONArray("body");
            final int n = geodata.length();
//            System.out.println(obj.getString("body"));
            System.out.println(parseTree);


        }catch (Exception e){
            System.out.println(e);
        }

        try{
            CharStream charStream = CharStreams.fromString(a);
            JavaScriptLexer javaScriptLexer =new JavaScriptLexer(charStream);
            CommonTokenStream commonTokenStream = new CommonTokenStream(javaScriptLexer);
            JavaScriptParser javaScriptParser = new JavaScriptParser(commonTokenStream);
            javaScriptParser.removeErrorListeners();
            javaScriptParser.addErrorListener(new JavaScriptErrorListner());
            ParseTree parseTree = javaScriptParser.program();
            LS ls = new LS();

            javaScriptParser.setErrorHandler(new ErrorCheck(ls));
            System.out.println(javaScriptParser);

//            System.out.println(parseTree.toStringTree(javaScriptParser));
//            String pt = parseTree.toStringTree(javaScriptParser);
//            JavaScriptErrorListner listener = new JavaScriptErrorListner();
//            javaScriptParser.addErrorListener(listener);
//            javaScriptParser.statement();
//            System.out.println("hii "+ javaScriptParser.getContext());
            System.out.println(toJson(parseTree,true));

        }catch (Exception e){
//            System.out.println(e);

        }

//        String code = "function a() { var b = 5; } function c() { }";
//
//        Options options = new Options("nashorn");
//        options.set("anon.functions", true);
//        options.set("parse.only", true);
//        options.set("scripting", true);
//
//        ErrorManager errors = new ErrorManager();
//        Context contextm = new Context(options, errors, Thread.currentThread().getContextClassLoader());
//        Context.setGlobal(contextm.createGlobal());
//        String json = ScriptUtils.parse(code, "<unknown>", false);
//        System.out.println(json);


    }
}
