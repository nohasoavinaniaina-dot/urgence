package mg.nyaina.urgences;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

/**
 * Impression / export PDF : ouvre la fenêtre d'impression Android
 * (choisir « Enregistrer au format PDF » pour obtenir un PDF).
 */
@CapacitorPlugin(name = "PrintHtml")
public class PrintHtmlPlugin extends Plugin {

    // Référence conservée pour éviter que la WebView soit ramassée avant la fin de l'impression
    private WebView printView;

    @PluginMethod
    public void print(final PluginCall call) {
        final String html = call.getString("html");
        final String name = call.getString("name", "Document");
        if (html == null) {
            call.reject("Contenu manquant");
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    final WebView wv = new WebView(getActivity());
                    printView = wv;
                    wv.setWebViewClient(new WebViewClient() {
                        private boolean done = false;

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            if (done) return;
                            done = true;
                            try {
                                PrintManager pm = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);
                                PrintDocumentAdapter adapter = view.createPrintDocumentAdapter(name);
                                PrintAttributes attrs = new PrintAttributes.Builder()
                                        .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                                        .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                                        .build();
                                pm.print(name, adapter, attrs);
                                call.resolve();
                            } catch (Exception e) {
                                call.reject(String.valueOf(e.getMessage()));
                            }
                        }
                    });
                    wv.loadDataWithBaseURL("https://localhost/", html, "text/html", "UTF-8", null);
                } catch (Exception e) {
                    call.reject(String.valueOf(e.getMessage()));
                }
            }
        });
    }
}
