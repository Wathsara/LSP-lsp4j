# LSP-lsp4j

### How to Run

#### step 1
`mvn clean install`l

#### step 2
Go to the Client Folder and run the Following Command

`npm i && npm run compile && npm run launch`


Make sure Change inside the `extension.ts` file which is in Client/src

```
const serverOptions: Executable = {
		command: "JAVA_HOME",
		args: ["-jar", "Path_to_Project/target/template-languageserver-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
	}
```
Then Create a file with .csv format and `press ctrl+space`, You will get The Suggestions.
