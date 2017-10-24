CC = scalac

BIN_DIR = bin/
SRC_DIR = src/

DELIV_PATH = deliverables
RAPPORT = requirements
EXEC_FILE = test.scala
MAIN_CLASS = HelloWorld

all: $(BIN_DIR)$(EXEC_FILE)

$(BIN_DIR)$(EXEC_FILE): 
	$(CC) $(SRC_DIR)$(EXEC_FILE) -d $(BIN_DIR)

run:
	scala -classpath "./$(BIN_DIR)" $(MAIN_CLASS)

clean:
	rm -rf */*.class

mrproper: clean
	rm -rf $(EXEC_FILE)

pdf:
	pdflatex -output-directory $(DELIV_PATH)/ $(RAPPORT).tex 
	rm */*.aux */*.log

tar: clean
	tar -zcvf PooCAv_Project_G7.tar.gz .gitignore Makefile LICENSE projetv1.pdf README.md UML_v1.pdf deliverables/ bin/ src/