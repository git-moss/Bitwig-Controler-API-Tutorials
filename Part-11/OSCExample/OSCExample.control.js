loadAPI(7);

host.setShouldFailOnDeprecatedUse(true);
host.defineController("MOSS", "OSCExample", "0.1", "8ca40c37-78c9-4c8c-932a-19a835cdbc06", "MOSS");

var connection = null;

function init()
{
   var oscModule = host.getOscModule ();

   // Sending
   connection = oscModule.connectToUdpServer ("127.0.0.1", 9000, oscModule.createAddressSpace ());

   // Receiving
   var addressSpace = oscModule.createAddressSpace ();
   addressSpace.setShouldLogMessages (true);
   addressSpace.registerMethod ("/whatever", ",s", "A test function", function (source, message)
   {
      println ("Received: " + message.getAddressPattern () + " " + message.getTypeTag () + " " + message.getString (0));
   });
   addressSpace.registerDefaultMethod (function (source, message)
   {
      println ("Received unknown: " + message.getAddressPattern ());
   });
   oscModule.createUdpServer (8000, addressSpace);

   println("OSCExample initialized!");
}

function sendOSCMessage ()
{
   // connection.startBundle ();
   connection.sendMessage ("/hello/world", "Good Morning!");
   // connection.endBundle ();
}


function flush()
{
}

function exit()
{
}