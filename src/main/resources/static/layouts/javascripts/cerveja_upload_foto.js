var Brewer = Brewer || {};

Brewer.UploadFoto = (function () {
    function UploadFoto() {
        this.campoNomeFoto = $('input[name=foto]');
        this.campoFormatoFoto = $('input[name=contentType]');
        this.painelUploadFoto = $('#upload-drop');
    }
    UploadFoto.prototype.iniciar = function () {
        let settings = {
            type: 'json',
            filelimit: 1,
            allow: '*.(jpg|jpeg|png)',
            action: '/brewer/fotos',
            complete: onUploadCompleto.bind(this)
        };
        UIkit.uploadSelect($('#upload-select'), settings);
        UIkit.uploadDrop($('#upload-drop'), settings);
        this.template = Handlebars.compile($('#foto-cerveja').html());
        if (this.campoNomeFoto.val()) {
            onUploadCompleto.call(this, {nome: this.campoNomeFoto.val(), contentType: this.campoFormatoFoto.val()});
        }
    };
    function onUploadCompleto(r) {
        this.campoNomeFoto.val(r.nome);
        this.campoFormatoFoto.val(r.contentType);
        let fotoCerveja = this.template({nomeFoto: r.nome});
        this.painelUploadFoto.addClass('hidden');
        $('.js-foto-cerveja').append(fotoCerveja);
        $('.btn-link').on('click', onRemoveFoto.bind(this));
    }
    function onRemoveFoto() {
        console.log('remove...');
        $('.js-container-foto').remove();
        this.painelUploadFoto.removeClass('hidden');
        this.campoNomeFoto.val('');
        this.campoFormatoFoto.val('');
    }
    return UploadFoto;
})();

$(function() {
    var uploadFoto = new Brewer.UploadFoto();
    uploadFoto.iniciar();
});
