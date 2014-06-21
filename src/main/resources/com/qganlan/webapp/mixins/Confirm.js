Confirm = Class.create({
	initialize : function(elementId, message) {
		this.message = message;
		Event.observe($(elementId), 'click', this.doConfirm.bindAsEventListener(this));
	},

	doConfirm : function(e) {
		if (!confirm(this.message)) {
			e.stop();
		}
	}
})

Tapestry.Initializer.confirm = function(spec) {
	new Confirm(spec.elementId, spec.message);
}
