rule plist_file_vulns
{
    strings:
        $b = "NSTemporaryExceptionAllowsInsecureHTTPLoads" nocase ascii wide
        $c = "NSAllowsArbitraryLoads" nocase ascii wide
    condition:
        any of them
}

rule debug_allow
{
    strings:
        $a = "get-task-allow" nocase ascii wide
    condition:
        any of them
}

rule api_key
{
    strings:
        $api_key = "[a-z0-9][a-z0-9]*-[a-z0-9][a-z0-9]*-[a-z0-9]"
        $b = "(Api-Key|ApiKey|Api_Key)" nocase ascii wide
        $key_token = ".*[A-Za-z0-9\\-\\]+(key|token)$" nocase ascii wide
    condition:
        any of them
}

rule deprecated_UIWEBVIEW
{
    strings:
        $i = "UIWebView"  nocase ascii wide  
    condition:
        any of them
}

rule vulnerable_bof_functions
{
    strings:
        $a = "_strncpy" nocase ascii wide
        $b = "_strcpy" nocase ascii wide
        $c = "_strlen"  nocase ascii wide 
        $d = "_memcpy"  nocase ascii wide 
        $e = "_fopen"   nocase ascii wide 
        $f = "_random"  nocase ascii wide 
        $g = "_NSLOG"  nocase ascii wide 
        $h = "_malloc"   nocase ascii wide 

    condition:
        any of them
}

rule admin_pass
{
    strings:
        $a = "admin" nocase ascii wide
        $ht_passwd = "^htpasswd Hash$" nocase ascii wide
    condition:
        any of them
}

rule npm_pip
{
    strings:
        $nmp_token = "^npm authToken$"
        $pip_passwd = "^pip password$" = "string"
    condition:
        any of them
}

rule password
{
    strings:
        $c = "(pass|password)" nocase ascii wide
        $e = "^\\S*(passwords?|passwd|pass|pwd)_?(hash)?[0-9]*$" nocase ascii wide = "string"
    condition:
        any of them
}

rule user
{
    strings:
        $b = "user" nocase ascii wide
    condition:
        any of them

}

rule aws_id
{
    strings:
        $aws_id = "^(?=.*[A-Z])(?=.*[0-9])A(AG|CC|GP|ID|IP|KI|NP|NV|PK|RO|SC|SI)A[A-Z0-9]{16}$"
    condition:
        any of them
}

rule aws_key
{
    strings:
                $aws_key = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9\\+\\/]{40}$" = "string"
    condition:
        any of them
}
rule aws_token
{
    strings:
        $aws_token = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9\\+\\/]{270,450}$"
    condition:
        any of them
}

rule comments
{
    strings:
        $comments = "<!--"
    condition:
        any of them
}

rule credentials_creditcard
{
    strings:
        $creds = "Credentials" nocase ascii wide
        $credit_card = "^[0-9]{14,}$"
    condition:
        any of them
}

rule path
{
    strings:
        $path = "^((([A-Z]|file|root):)?(\\.+)?[/\\]+).*$"
    condition:
        any of them
}

rule githubtoken
{
    strings:
        $github_token = "^(gh[pous]_[a-zA-Z0-9]{36}|ghr_[a-zA-Z0-9]{76})$"
    condition:
        any of them
}

rule uri 
{
    strings:
        $uri = "http|ftp|smtp|scp|ssh|jdbc[:\\w\\d]*|s3)s?://?.+"
    condition:
        any of them
}

rule private_key
{
    strings:
        $private_key = ".*[\\-]{3,}BEGIN (RSA|DSA|EC|OPENSSH|PRIVATE)? ?(PRIVATE)? KEY[\\-]{3,}.*"
    condition:
        any of them
}

rule misc_strings
{
    strings:
      
       $secrets = ".*(secrets?)_?(hash)?$"
       $slac_webhook = "https://hooks\\.slack\\.com/services/[A-Z0-9]{9}/[A-Z0-9]{9}/[a-zA-Z0-9]+"
       $misc = ".*(rsa|dsa|ed25519|ecdsa|pem|crt|cer|ca-bundle|p7b|p7c|p7s|(private-)?key|keystore|jks|pkcs12|pfx|p12|asc)$"
       $misc2 = ".*(dockercfg|npmrc|pypirc|pip.conf|terraform.tfvars|env|cfg|conf|config|ini|s3cfg)$"
       $misc3 = ".*(\\.aws/credentials|htpasswd|(\\.|-)?netrc|git-credentials|gitconfig|gitrobrc)$"
       $misc4 = ".*(password|credential|secret)(\\.[A-Za-z0-9]+)?$"
       $misc5 = ".*(servlist-?\\.conf|irssi/config|keys\\.db)$"       
       $misc6 = ".*(settings\\.py|database\\.yml)$"               
       $misc7 = ".*(config(\\.inc)?|LocalSettings)\\.php)$"        
       $misc8 = ".*(secret-token|omniauth|carrierwave|schema|knife)\\.rb)$"
       $misc9 = ".*(accounts|dbeaver-data-sources|BapSshPublisherPlugin|credentials|filezilla|recentservers)\\.xml)$"
       $misc10 = ".*(ba|z|da)?sh-history|(bash|zsh)rc|(bash-|zsh-)?(profile|aliases)$"
       $misc11 = ".*(kdbx?|(agile)?keychain|key(store|ring)$"                                
       $misc12 = ".*(mysql-history|psql-history|pgpass|irb-history )$"                             
       $misc13 = ".*(log|pcap|sql(dump)?|gnucash|dump)$"        
       $misc14 = ".*(backup|back|bck)$"                                                 
       $misc15 = ".*(kwallet|tblk|pubxml(\\.user))?$"                                                
       $misc16 = ".*(Favorites\\Credentials\\.user\\.xpl)$"
       $misc17 = ".*(proftpdpasswd|robomongo\\.json )$"

    condition:
        any of them
}
