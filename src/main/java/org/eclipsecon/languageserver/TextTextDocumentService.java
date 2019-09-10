package org.eclipsecon.languageserver;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;


public class TextTextDocumentService implements TextDocumentService {

    public TextTextDocumentService(TextLanguageServer textLanguageServer) {

    }
    @Override
    public CompletableFuture<Either<List<CompletionItem>, CompletionList>>completion(
            CompletionParams completionParams) {
        List<CompletionItem> completionItems = new ArrayList<>();
        completionItems.add(new CompletionItem(completionParams.toString()));
        completionItems.add(new CompletionItem(completionParams.getContext().getTriggerKind().toString()));
        completionItems.add(new CompletionItem("function functionName()={}"));
        completionItems.add(new CompletionItem("First World"));
        completionItems.add(new CompletionItem("Second World"));
        completionItems.add(new CompletionItem("Third World"));
        completionItems.add(new CompletionItem("Forth World"));
        return CompletableFuture.completedFuture(Either.forLeft(completionItems));

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

    }

    @Override
    public void didChange(DidChangeTextDocumentParams params) {

    }

    @Override
    public void didClose(DidCloseTextDocumentParams params) {
    }

    @Override
    public void didSave(DidSaveTextDocumentParams params) {
    }

}
