/* Application Android : window.open + print() ne fonctionnent pas dans une WebView.
   On les remplace par la fenêtre d'impression Android (choisir « Enregistrer au format PDF »). */
(function(){
  var C=window.Capacitor;
  if(!C||!C.isNativePlatform||!C.isNativePlatform())return;
  window.open=function(){
    var buf=[];
    var w={
      closed:false,
      document:{
        write:function(){for(var i=0;i<arguments.length;i++)buf.push(String(arguments[i]))},
        writeln:function(){for(var i=0;i<arguments.length;i++)buf.push(String(arguments[i]));buf.push('\n')},
        open:function(){},close:function(){}
      },
      focus:function(){},close:function(){this.closed=true},
      print:function(){
        var html=buf.join('');
        var m=html.match(/<title>([^<]*)<\/title>/i);
        var name=(m?m[1]:'Ny Aina Med').replace(/&amp;/g,'&').replace(/[\\\/:*?"<>|]+/g,' ').trim()||'Ny Aina Med';
        C.nativePromise('PrintHtml','print',{html:html,name:name}).catch(function(e){
          alert('Impression impossible : '+((e&&e.message)||e));
        });
      }
    };
    return w;
  };
})();
