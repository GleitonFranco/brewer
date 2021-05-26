var Brewer = Brewer || {};
Brewer.EstiloCadastroRapido = (function() {
	function EstiloCadastroRapido() {
		this.modal = $('#estiloModal');
		this.form = this.modal.find('form');
		this.inputNome = this.form.find('#nomeEstilo');
		this.btSalvar = this.modal.find('.js-modal-cadastro-estilo-salvar-btn');
		this.containerMensagemErro = $('.js-mensagem-cadastro-rapido-estilo');
		this.url = this.form.attr('action');
	}

	EstiloCadastroRapido.prototype.iniciar = function() {
		this.form.on('submit', e => e.preventDefault());
		this.modal.on('shown.bs.modal', () => this.inputNome.focus());
		this.modal.on('hide.bs.modal' , () => {
			this.inputNome.val('');
			this.modal.find('.form-group').removeClass('has-error');
			this.containerMensagemErro.addClass('hidden');
		});
		this.btSalvar.on('click', () => {
			var novoEstilo = this.inputNome.val().trim();
			$.ajax({
				url : this.url,
				method: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({'nome' : novoEstilo}),
				success: estilo => {
					console.log('success! ', estilo);
					var comboEstilos = $('#estilo');
					this.comboEstilos.append('<option value='+estilo.codigo+'>'+estilo.nome+'</option>');
					this.comboEstilos.val(estilo.codigo);
					this.modal.hide();
				},
				error: obj => {
					console.log('Erro!', obj);
					this.containerMensagemErro.removeClass('hidden');
					this.containerMensagemErro.html('<span>Estilo já existente em banco!</span>');
					this.modal.find('.form-group').addClass('has-error');
				}
			});
		});
	}
	
	return EstiloCadastroRapido;
})();

$(function() {
	var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();
});
