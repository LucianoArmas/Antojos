
    let typeSelect = document.getElementById("editType");
    let drinkInput = document.getElementById("drinkInput");
    let foodInput = document.getElementById("foodInput");


    drinkInput.style.display = "none";
    foodInput.style.display = "none";



    typeSelect.addEventListener("change", ()=>{

        if (typeSelect.value === "drink") {
            drinkInput.style.display = "block";
            foodInput.style.display = "none";
        } else if (typeSelect.value === "food") {
            foodInput.style.display = "block";
            drinkInput.style.display = "none";
        }
    })


