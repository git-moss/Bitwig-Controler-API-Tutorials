loadAPI(7);

host.setShouldFailOnDeprecatedUse(true);
host.defineController("Yamaha", "MOXF", "0.1", "bdf1fb54-4642-41d0-9851-460bc63fcf4e", "Jürgen Moßgraber");

function init()
{
   println("MOXF initialized!");
}

function flush()
{
   println ("Flush called.");
}

function exit()
{
   println("Exited!");
}