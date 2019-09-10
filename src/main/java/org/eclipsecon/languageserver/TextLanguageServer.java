package org.eclipsecon.languageserver;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

public class TextLanguageServer implements LanguageServer {

	private TextDocumentService textService;
	private WorkspaceService workspaceService;
	LanguageClient client;

	public TextLanguageServer() {
		textService = new TextTextDocumentService(this);
		workspaceService = new TextWorkspaceService();
	}
	
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		;
		final InitializeResult res = new InitializeResult(new ServerCapabilities());
		res.getCapabilities().setCodeActionProvider(Boolean.FALSE);
		res.getCapabilities().setCompletionProvider(new CompletionOptions());
		res.getCapabilities().setDefinitionProvider(Boolean.FALSE);
		res.getCapabilities().setHoverProvider(Boolean.FALSE);
		res.getCapabilities().setReferencesProvider(Boolean.FALSE);
		res.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Full);
		res.getCapabilities().setDocumentSymbolProvider(Boolean.FALSE);
		return CompletableFuture.supplyAsync(() -> res);


	}

	public CompletableFuture<Object> shutdown() {
		return CompletableFuture.supplyAsync(() -> Boolean.TRUE);
	}

	public void exit() {
	}

	public TextDocumentService getTextDocumentService() {
		return this.textService;
	}

	public WorkspaceService getWorkspaceService() {
		return this.workspaceService;
	}

	public void setRemoteProxy(LanguageClient remoteProxy) {
		this.client = remoteProxy;
	}
	
}
