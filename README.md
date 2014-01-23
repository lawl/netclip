netclip
=======

netclip is a small utility to share your clipboard across the network. If you have multiple computers on your homenetwork and sometimes have to share small strings between computers a shared clipboard is certainly handy. Netclip does exactly that. It's written in java so you can share your clipboard between OSX, Windows and real operating systems.

How to use
----------
A sample configuration is shipped with netclip (`config.properties.sample`). Rename it to `config.properties` and **CHANGE THE KEY**.
Below is a sample of such a configuration.

    # Choose some port above 1024 (unprivileged)
    # You probably won't need to change this except if there's already
    # something listening on that port or you want to run multiple
    # independent netclip clusters.

    netclip.port=6234

    # CHANGE THIS KEY TO SOMETHING SECRET!
    # Need to be the same on all machines

    netclip.key=topsecret

    # Broadcast
    # Change this to the broadcast address if boxes that should
    # receive netclip notifications.

    netclip.broadcast=255.255.255.255

    # Share clipboard by default
    # If set to false the system will not anounce clipboard changes
    # Or allow others to pull their clipboard via TCP
    # You can change this setting on the fly from the tray menu
    # This is just the default setting.
    # If set to false the system will only listen for clipboard
    # changes from other systems. But not share theirs.

    netclip.shareclipboard=true
    
    # if you have a crypto crippled JVM, because you are a terrorist,
    # you might get a message like this one:
    #
    #    java.security.InvalidKeyException: Illegal key size
    #
    # This means america has decided you're a terrorist and are therefore
    # not allowed to use 256 bit keys. Please turn yourself in at the
    # department of homeland security, the NSA or whatever you think is
    # appropriate. 
    #
    # Or you know, just set this value to 128 if you can live with shitty
    # crypto. Or just uninstall that oracle adware jvm and install
    # openjdk (http://openjdk.java.net/)
    
    netclip.aeskeysize=256


Security
--------
netclip broadcasts a message to the broadcast address defined in the configuration. Whenever other netclip instances get that message they are free to open a TCP connection to that node and fetch the clipboard content. All Clipboard data on the wire is encrypted using AES (keysize configurable in config, due to export limitations in certain JVM's). For encryption a key is derived from the pre shared key you have to enter in the config.
I'm by no means a crypto expert and don't claim my crypto is secure. It's probably enough against a random eavesdropper. But a dedicated one might crack it.

So uh heres what I think you need to know about the crypto. You can then decide for yourself if you trust it or tell me where i totally fucked up :)
(yeah yeah, don't roll your own crypto, leave me alone, TLS sucks)

* The key is derived from the PSK using `PBKDF2-with-HMAC-SHA1` at 65536 rounds with a static salt (the static salt isn't optimal but i'm lazy).
* It is then encrypted using `AES-CBC-PKCS5Padding`.
* A new seed from SecureRandom is every time for the IV (I'm in ur computer, draining your entropies)

Known problems:
* No forward secrecy.
* You could probably build a rainbow table for the PSK's because I used a static salt.
* Data is not tamper resistant.

I might fix these sometimes later in the future. However because this is all in my internal network, on the wire, no WLAN, I don't care too much right now.
