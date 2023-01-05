/*let objArray = []
obj = {
    name:"SomeName",
    creator:"Someone"
    ,assigned:"HealthMinister"
}
obj1 =
{
    name:"Another Name",
    creator:"AnotherPerson"
    ,assigned:"WealthMinister"
}
objArray.push(obj);
objArray.push(obj1);

let names = ["Some","Gloe","Slowie","Blowie","Nat","Gloe"]
console.log(names)
names.filter((name)=>{
    return name !== "Gloe";
})
console.log(names)

objArray.map((temp)=>{
    return temp.name+" Add";
})

addingCards(objArray)


function addingCards(objArray)
{
    const i =0;
    objArray.forEach(element => {
        console.log(i)
        const op = '<span class="card">'+
        '<h2 class="title">'+element.name+'</h2>'+
        '<h3 class="creator">'+element.creator+'</h3>'+
        '<h3 class="assigned">'+element.assigned+'</h3>'+
        '</span>';
        div = document.createElement("div")
        div.innerHTML = op;        
        document.body.appendChild(div)
    });
    console.log("The element at third position is"+objArray[2])
}*/
let objArray = []

fetch('./info.json')
    .then((response) => response.json())
    .then((json) => cardAddition(json.cards));

function showJson(json)
{
    console.log("From the function:"+json.cards);
}

obj = {
    title:"SomeName",
    creator:"Someone"
    ,assigned:"HealthMinister"
}
obj1 =
{
    title:"Another Name",
    creator:"AnotherPerson"
    ,assigned:"WealthMinister"
}
objArray.push(obj);
objArray.push(obj1);

cardAddition(objArray);
function cardAddition(objArray)
{
    objArray.forEach(obj => {
        const card = '<div class="card">'+
        '<p class="card-title"><span class="title-heading">Title:</span>'+obj.title+'</p>'+
        '<p class="card-assigned"><span class="assigned-heading">Assigned:</span>'+ obj.assignedTo+'</p>'+
        '<p class="card-created"><span class="created-heading">Created:</span>'+ obj.createdBy+'</p>'+
        '</div>';
        const div = document.createElement("div");
        div.innerHTML = card;
        if(obj.column === "todo")
        {
            var container1 = document.getElementsByClassName("container1");
            container1[0].appendChild(div);    
        }
        else if(obj.column === "inProgress")
        {
            var container2 = document.getElementsByClassName("container2");
            container2[0].appendChild(div);            
        }
        else if(obj.column === "done")
        {
            var container3 = document.getElementsByClassName("container3");
            container3[0].appendChild(div);    
        }
    }); 
    
}