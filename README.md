# JavaSam

**JavaSam** is a Java-based wrapper around the classic speech synthesizer **SAM** (Software Automatic Mouth), originally written in 6502 assembler and later ported to C. This version uses a custom `.class` file (`SamClass16`) generated via **NestedVM** to run the C-version of SAM directly on the JVM — no native libraries required.

## Features

- Converts text to speech using SAM’s iconic robotic voice
- Works on standard JVMs and Android (no JNI, no native binaries)
- CLI and GUI modes included
- Fully portable `.class`-based implementation (approx. 500 KB)

## Usage

### Command Line

You can run JavaSam from the terminal:

```bash
java -jar JavaSam.jar "Hello world!"

