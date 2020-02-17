JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Data.java \
		HourlyInterval.java \
		Device.java \
		Room.java \
		House.java \
		Main.java 

default: classes

classes: $(CLASSES:.java=.class)

run: default
	java Main
clean:
		$(RM) *.class