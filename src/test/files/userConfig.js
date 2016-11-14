function searchProductToAdd() {
	var ul = document.getElementById('list_products_to_add');
	var search = document.getElementById('search_product_to_add').value;

	addOrRemoveClass(ul, search);
}

function searchProductToRemove() {
	var ul = document.getElementById('list_products_added');
	var search = document.getElementById('search_product_to_remove').value;

	addOrRemoveClass(ul, search);
}

function addOrRemoveClass(ul, search) {
	var search_length = search.length;

	for(var i = 0, li; li = ul.children[i]; i++) {
		if(li.tagName == 'LI') {
			var product_name = li.getElementsByClassName('product_name')[0].innerHTML;

			if(product_name != "Aucun résultat") {
				if(product_name.substring(0, search_length).toLowerCase() != search.toLowerCase()) {
					li.classList.add('hide');
				} else {
					li.classList.remove('hide');
				}
			}
	  	}
	}

	noResult(ul, search_length);
}

function noResult(ul, search_length) {
	var allDisabled = true;

	if(search_length > 0) {
		for(var i = 0, li; (li = ul.children[i]) && allDisabled; i++) {
			if(li.tagName == "LI") {
				var li = ul.children[i];
				var className = li.className;
				var product_name = li.getElementsByClassName('product_name')[0].innerHTML;

				if(!className.includes('hide') && product_name != "Aucun résultat") {
					allDisabled = false;
				}
			}
		}
	} else {
		allDisabled = false;
	}

	if(allDisabled) {
		ul.getElementsByClassName('no_result')[0].classList.remove('hide');
	} else {
		ul.getElementsByClassName('no_result')[0].classList.add('hide');
	}
}

function addProduct(object) {
	var product_name = object.getAttribute('data-name');
	var product_position = object.getAttribute('data-position');
	var list_products_added = document.getElementById('list_products_added');

	var html_string = `
		<li class="collection-item">
			<span class="product_name">` +  product_name + `</span>
			<a class="secondary-content left" data-name="` + product_name + `" data-position="` + product_position + `" onClick="removeProduct(this)">
				<i class="material-icons">keyboard_arrow_left</i>
			</a>
		</li>`;

	//$(list_products_added).find(':nth-child(' + (product_position - 1) + ')').after(html_string);
	//Tri des li. Ne marche pas, le product_position doit etre opti 
	//Le precedent n'est pas forcement dans la liste ou l'on veut ajouter l'getElementById
	list_products_added.innerHTML += html_string;
	removeElement(object);
}

function removeProduct(object) {
	var product_name = object.getAttribute('data-name');
	var product_position = object.getAttribute('data-position');
	var list_products_to_add = document.getElementById('list_products_to_add');

	var html_string = `
		<li class="collection-item">
			<span class="product_name">` + product_name + `</span>
			<a class="secondary-content" data-name="` + product_name + `" data-position="` + product_position + `" onClick="addProduct(this)">
				<i class="material-icons">keyboard_arrow_right</i>
			</a>
		</li>`;

	//$(list_products_to_add).find(':nth-child(' + (product_position - 1) + ')').after(html_string);
	list_products_to_add.innerHTML += html_string;
	removeElement(object);
}

function removeElement(object) {
	var element = object.parentNode;
	var parent = object.parentNode.parentNode;

	parent.removeChild(element);
}

function addAllProduct() {
	var list_products_to_add = document.getElementById('list_products_to_add');
	var list_length = list_products_to_add.childElementCount;

	if(list_length > 1) {
		for(var i = 0; i < list_length - 1; i++) {
			var li = list_products_to_add.children[1];

			if(!li.className.includes('no_result')) {
				addProduct(li.children[1]);
			}
		}
	}
}

function removeAllProduct() {
	var list_products_added = document.getElementById('list_products_added');
	var list_length = list_products_added.childElementCount;

	if(list_length > 1) {
		for(var i = 0; i < list_length - 1; i++) {
			var li = list_products_added.children[1];

			if(!li.className.includes('no_result')) {
				removeProduct(li.children[1]);
			}
		}
	}
}