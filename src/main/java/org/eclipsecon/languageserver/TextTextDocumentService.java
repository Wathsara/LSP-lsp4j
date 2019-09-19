package org.eclipsecon.languageserver;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;

public class TextTextDocumentService implements TextDocumentService {


    public TextTextDocumentService(TextLanguageServer textLanguageServer) {
    }
    Map<String, String> contentMap = new HashMap<String, String>();
    String content= "null";
    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
            CompletionParams completionParams) {

        List<CompletionItem> completionItems = new ArrayList<>();

        int a = completionParams.getPosition().getLine();

        if (a == 0) {
            completionItems.add(new CompletionItem());
            if(contentMap.get(completionParams.getTextDocument().getUri()) != null){
                completionItems.add(new CompletionItem(contentMap.get(completionParams.getTextDocument().getUri())));
            }
            completionItems.add(new CompletionItem("First World"));
            completionItems.add(new CompletionItem("Second World"));
            completionItems.add(new CompletionItem("Third World"));
            completionItems.add(new CompletionItem("Forth World"));
            completionItems.add(new CompletionItem("Hii World"));
            return CompletableFuture.completedFuture(Either.forLeft(completionItems));
        } else {
            if(contentMap.get(completionParams.getTextDocument().getUri()) != null){
                completionItems.add(new CompletionItem(contentMap.get(completionParams.getTextDocument().getUri())));
            }
            completionItems.add(new CompletionItem("1 World"));
            completionItems.add(new CompletionItem("2 World"));
            completionItems.add(new CompletionItem("3 World"));
            completionItems.add(new CompletionItem("4 World"));
            completionItems.add(new CompletionItem("5 World"));
            return CompletableFuture.completedFuture(Either.forLeft(completionItems));
        }


    }


    @Override
    public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
        return null;
    }

    @Override
    public CompletableFuture<Hover> hover(TextDocumentPositionParams position) {
        return null;
    }


    @Override
    public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams position) {
        return null;
    }

    @Override
    public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(TextDocumentPositionParams position) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams position) {
        return null;
    }

    @Override
    public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(DocumentSymbolParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
        return null;
    }

    @Override
    public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
        return null;
    }

    @Override
    public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
        return null;
    }

    @Override
    public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
        return null;
    }


    @Override
    public void didOpen(DidOpenTextDocumentParams params) {

        String filePath = params.getTextDocument().getUri();
        filePath = filePath.substring(7);
        contentMap.put(filePath, filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        // delete the last new line separator
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(stringBuilder.toString() != null){
            contentMap.put(filePath, stringBuilder.toString());
        }else{
            contentMap.put(filePath, "nothing");
        }


        content = "open";
    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {

        content = params.getContentChanges().get(0).getText();
        if(content != null){
            contentMap.put(params.getTextDocument().getUri(), content);
        }else{
            contentMap.put(params.getTextDocument().getUri(), "nothing");
        }

    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
        contentMap.remove(params.getTextDocument().getUri());
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
    }

}
