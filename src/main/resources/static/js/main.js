$(function () {

    //Show inventory changing form
    $(document).on('click', '.inventory-change', function () {
        const link = $(this);
        const inventoryId = link.data('id');
        const inventoryCode = '<form>' +
            '<h2>Change inventory name</h2>' +
            '<label>Name:</label>' +
            '<input type="text" name="name" value="">' +
            '<hr><a href="#" class="change-inventory" data-id="' +
            inventoryId + '">Change</a></form><br>';
        $('#inventory-change-form')
            .append('<div>' + inventoryCode + '</div>');
        $('#inventory-change-form').css('display', 'flex');

    });

    //Closing inventory change form
    $('#inventory-change-form').click(function (event) {
        if (event.target === this) {
            $(this).html('')
            $(this).css('display', 'none');
        }
    });

    //Show cupboard changing form
    $(document).on('click', '.cupboard-change', function () {
        const link = $(this);
        const cupboardId = link.data('id');
        const cupboardCode = '<form>' +
            '<h2>Change cupboard name</h2>' +
            '<label>Name:</label>' +
            '<input type="text" name="name" value="">' +
            '<hr><a href="#" class="change-cupboard" data-id="' +
            cupboardId + '">Change</a></form><br>';
        $('#cupboard-change-form')
            .append('<div>' + cupboardCode + '</div>');
        $('#cupboard-change-form').css('display', 'flex');

    });

    //Closing cupboard changing form
    $('#cupboard-change-form').click(function (event) {
        if (event.target === this) {
            $(this).html('')
            $(this).css('display', 'none');
        }
    });

    //Deleting inventory
    $(document).on('click', '.inventory-delete', function () {
        const link = $(this);
        const inventoryId = link.data('id');
        $.ajax({
            method: "DELETE",
            url: '/inventories/' + inventoryId,
            success: function (response) {
                const code = '<span>Inventory has deleted!</span>';
                link.parent().append(code);
            },
            error: function (response) {
                if (response.status === 404) {
                    alert('Inventory not found!');
                }
            }
        });
        return false;
    });

    //Deleting cupboard
    $(document).on('click', '.cupboard-delete', function () {
        const link = $(this);
        const cupboardId = link.data('id');
        $.ajax({
            method: "DELETE",
            url: '/cupboards/' + cupboardId,
            success: function (response) {
                const code = '<span>Cupboard has deleted!</span>';
                link.parent().append(code);
            },
            error: function (response) {
                if (response.status === 404) {
                    alert('Cupboard not found!');
                }
            }
        });
        return false;
    });

    //Change inventory
    $(document).on('click', '.change-inventory', function () {
        const link = $(this);
        const inventoryId = link.data('id');
        let data = $('#inventory-change-form form').serialize();
        data += '&id=' + inventoryId;
        $.ajax({
            method: "PUT",
            url: '/inventories?' + data,
            success: function (response) {
                const code = '<span><br>Inventory has changed!</span>';
                link.parent().append(code);
            },
            error: function (response) {
                if (response.status === 404) {
                    alert('Inventory not found!');
                }
            }
        });
        return false;
    });

    //Change cupboard
    $(document).on('click', '.change-cupboard', function () {
        const link = $(this);
        const cupboardId = link.data('id');
        let data = $('#cupboard-change-form form').serialize();
        data += '&id=' + cupboardId;
        $.ajax({
            method: "PUT",
            url: '/cupboards?' + data,
            success: function (response) {
                const code = '<span><br>Cupboard has changed!</span>';
                link.parent().append(code);
            },
            error: function (response) {
                if (response.status === 404) {
                    alert('Cupboard not found!');
                }
            }
        });
        return false;
    });

    //Add inventory in cupboard
    $(document).on('click', '.add-inventory-in-cupboard', function () {
        const link = $(this);
        const cupboardId = link.data('id');
        let data = $('#add-inventory-in-cupboard-form form').serialize();
        data += '&cupboard_id=' + cupboardId;
        $.ajax({
            method: "PUT",
            url: '/inventory/2cupboard/add?' + data,
            success: function (response) {
                const code = '<span><br>Inventory has added!</span>';
                link.parent().append(code);
            },
            error: function (response) {
                if (response.status === 404) {
                    alert('Inventory not found!');
                }
            }
        });
        return false;
    });

    //Delete inventory in cupboard
    $(document).on('click', '.delete-inventory-in-cupboard', function () {
        const link = $(this);
        const inventoryId = link.data('id');
        $.ajax({
            method: "PUT",
            url: '/inventory/2cupboard/delete/' + inventoryId,
            success: function (response) {
                const code = '<span><br>Inventory has deleted!</span>';
                link.parent().append(code);
            },
            error: function (response) {
                if (response.status === 404) {
                    alert('Inventory not found!');
                }
            }
        });
        return false;
    });
});