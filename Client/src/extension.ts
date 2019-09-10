import { ExtensionContext } from 'vscode';

import { Executable, LanguageClient, LanguageClientOptions, StreamInfo } from 'vscode-languageclient';

export function activate(context: ExtensionContext) {

	const serverOptions: Executable = {
		command: "/usr/lib/jvm/java-8-openjdk-amd64/bin/java",
		args: ["-jar", "/home/wathsara/Desktop/Template/target/template-languageserver-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
	}

	// function serverOptions(): Thenable<StreamInfo> {
	//    const client = require("net").connect(8080)
	//    return Promise.resolve({
	//        writer: client,
	//        reader: client
	//    })
	// }

	const clientOptions: LanguageClientOptions = {
		documentSelector: [{ scheme: 'file', language: 'csv' }]
	}

	const disposable = new LanguageClient('csv', 'TextLanguageServer', serverOptions, clientOptions).start();
	context.subscriptions.push(disposable);
}