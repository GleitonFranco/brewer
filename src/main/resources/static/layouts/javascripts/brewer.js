var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({ decimal:',', thousands:'.' });
		this.plain.maskMoney({ precision: 0, thousands:'.' });		
	};
	
	return MaskMoney;
})();

Brewer.MaskPhoneNumber = (function () {
    let MaskPhoneNumber = function () {
        this.inputPhoneNumber = $('.js-phone-number');
    };
    MaskPhoneNumber.prototype.enable = function () {
        this.inputPhoneNumber.mask('(00) 00000-0000');
    };
    return MaskPhoneNumber;
} ());

$(function() {
    const maskMoney = new Brewer.MaskMoney();
    maskMoney.enable();

    const maskPhoneNumber = new Brewer.MaskPhoneNumber();
    maskPhoneNumber.enable();
});
