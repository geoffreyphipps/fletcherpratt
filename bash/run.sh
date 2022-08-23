
# Set your root directory for your own setup
export FP_ROOT="/Users/gphipps/me/games/E-H/FletcherPratt/fletcherpratt"

# These will also change, depending on how it was compiled
export CLASSPATH="$FP_ROOT/out/production/fletcherpratt:$CLASSPATH"
export CLASSPATH="$FP_ROOT/lib/commons-csv-1.1/commons-csv-1.1.jar:$CLASSPATH"

echo $CLASSPATH
mkdir -p "$FP_ROOT/output"

echo $CLASSPATH
java com.gphipps.fletcherpratt.ProcessAll  "$FP_ROOT/input"  "$FP_ROOT/output"
