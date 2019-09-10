'use strict';
exports.__esModule = true;
var path = require("path");
var vscode_1 = require("vscode");
// @ts-ignore
var vscode_languageclient_1 = require("vscode-languageclient");
var main = 'Main';
function activate(context) {
    var JAVA_HOME = process.env.JAVA_HOME;
    // in windows class path seperated by ';'
    var sep = process.platform === 'win32' ? ';' : ':';
    var customClassPath = vscode_1.workspace.getConfiguration('swaggerls').get('classpath');
    console.log("Custom Class Path: " + customClassPath);
    console.log("Using java from JAVA_HOME: " + JAVA_HOME);
    var excecutable = path.join('usr', 'lib', 'jvm', 'java-8-openjdk-amd64', 'bin', 'java');
    // let jarPath = path.join(__dirname, '..', 'launcher', 'ls-launcher.jar');
    var classPath = path.join(__dirname, '..', 'launcher', 'ls-launcher.jar');
    if (customClassPath) {
        classPath = classPath + sep + customClassPath;
    }
    var args = ['-cp', classPath];
    console.log("Custom Class Path: " + customClassPath);
    if (process.env.LSDEBUG === "true") {
        console.log('LSDEBUG is set to "true". Services will run on debug mode');
        args.push('-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005,quiet=y');
    }
    var serverOptions = {
        command: excecutable,
        args: args.concat([main]),
        options: {}
    };
    // Options to control the language client
    var clientOptions = {
        // Register the server for plain text documents
        documentSelector: [{ scheme: 'file', language: "plaintext" }]
    };
    // Create be deactivated on extension deactivation
    context.subscriptions.push(disposable);
}
exports.activate = activate;

