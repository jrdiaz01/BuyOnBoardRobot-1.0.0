#!/bin/sh

#set -x

#-------------------
#inicio de variable
#-------------------

#ROBOT_HOME="/home/srvltapiffp/BuyOnBoardRobot-1.0.0"; export ROBOT_HOME
ROBOT_HOME="/cygdrive/h/zenta/workspace/latam4v2/latam4/BuyOnBoardRobot-1.0.0/dist"; export ROBOT_HOME
ROBOT_NAME="BuyOnBoardRobot-1.0.0"; export ROBOT_NAME
#AQUÍ SE VA A CAMBIAR DEPENDIENDO DONDE SE ENCUENTRE UBICADO EL JDK
#JAVA_HOME="/opt/java/jdk1.8.0_79"; export JAVA_HOME
JAVA_HOME="/cygdrive/h/zenta/workspace/latam4v2/latam4/_jdk8"; export JAVA_HOME
MSG_SUCCESS="Proceso finalizado correctamente."; export MSG_SUCCESS
MSG_UNSUCCESS="Proceso finalizado con errores."; export MSG_UNSUCCESS

cd $ROBOT_HOME

#improving the process for importing libraries
CLASSPATH=$ROBOT_HOME/config:$ROBOT_HOME/BuyOnBoardRobot-1.0.0.jar
for lib in $ROBOT_HOME/lib/*; do 
     if [ -f "${lib}" ]; then  
         CLASSPATH="$CLASSPATH:$lib"; 
     fi 		 
done

#export the classpath for use by cygwin terminal for testing purpose
export BATCH_CP=`cygpath -wp ${CLASSPATH}`

echo classpath $CLASSPATH

#---------------------------------------------------
# Llamada a la aplicación en JAVA
#---------------------------------------------------

#calling the application from remote environment
#$JAVA_HOME/bin/java -cp $CLASSPATH com.latam.crs.buyonboard.process.wrapper.WrapperBuyOnBoard

#calling the application from local environment
$JAVA_HOME/bin/java -cp $BATCH_CP com.latam.crs.buyonboard.process.wrapper.WrapperBuyOnBoard

_RETURN=$? 

echo return $_RETURN

if [ $_RETURN -eq 0 ]
then
	echo $MSG_SUCCESS
	exit 0
else
	echo $MSG_UNSUCCESS
fi
exit $_RETURN
