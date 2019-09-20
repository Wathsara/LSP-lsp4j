package org.adaptive.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class JavaScriptErrorListner extends BaseErrorListener {
    private final List<SyntaxError> syntaxErrors = new ArrayList<>();
    private static HashMap<int[], String> recognizer = new HashMap<int[], String>();

    public HashMap<int[], String> getRecognizer() {
        return recognizer;
    }

    public void setRecognizer() {
        this.recognizer= new HashMap<int[], String>();
    }


    public JavaScriptErrorListner()
    {
        this.setRecognizer();
    }

    List<SyntaxError> getSyntaxErrors()
    {
        return syntaxErrors;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e)
    {

        List<String>stack=((Parser)recognizer).getRuleInvocationStack();
        this.recognizer.put((new int[]{line,charPositionInLine}),stack.get(0));
        underlineError(recognizer,(Token)offendingSymbol,line,charPositionInLine);
    }

    @Override
    public String toString()
    {
        return Utils.join(syntaxErrors.iterator(), "\n");
    }
    protected void underlineError(Recognizer recognizer,Token offendingToken,int line,int charPositionInLine){
        CommonTokenStream tokens=(CommonTokenStream)recognizer.getInputStream();
        String input=tokens.getTokenSource().getInputStream().toString();
        String[]lines=input.split("\n");
        String errorLine=lines[line-1];
        System.err.println(errorLine);
        for(int i=0;i<charPositionInLine;i++)
            System.err.print("");
        int start=offendingToken.getStartIndex();
        int stop=offendingToken.getStopIndex();
        if(start>=0&&stop>=0){
            for(int i=start;i<=stop;i++)
                System.err.print("^");
        }
        System.err.println();}
}
