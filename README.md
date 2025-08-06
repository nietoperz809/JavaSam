JavaSam

JavaSam is a Java-based wrapper around the classic speech synthesizer SAM (Software Automatic Mouth), originally written in 6502 assembler and later ported to C. This version uses a custom .class file (SamClass16) generated via NestedVM to run the C-version of SAM directly on the JVM — no native libraries required.

Features:
Converts text to speech using SAM’s iconic robotic voice
Works on standard JVMs and Android (no JNI, no native binaries)
CLI and GUI modes included
Fully portable .class-based implementation (approx. 500 KB)

Usage:

Command Line:
You can run JavaSam from the terminal:
java -jar JavaSam.jar "Hello world!"

GUI:
A simple GUI is included for entering text and playing it using SAM’s voice.

How It Works:
SamClass16 contains Java bytecode generated from SAM's C source using NestedVM
A custom class loader (ByteArrayClassLoader) loads the class at runtime
The method xmain(PrintStream, String[]) is invoked via reflection to process text input
Audio output is streamed to memory and played using Java audio APIs

Requirements:
Java 8 or higher
IntelliJ IDEA recommended for development (can be cloned directly)

License:
This project is based on publicly available C ports of the original SAM engine.
The Java wrapper and this repository are provided under an open license.

Credits:
Original SAM: Mark Barton (6502), C port by various contributors
Java bytecode version (SamClass16) created using NestedVM
Java wrapper and integration: Paul Sanders
README authored by ChatGPT


