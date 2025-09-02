using Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;
using Jimx.ListItemSelector.Application.ListItems.Commands.DeleteListItem;
using Jimx.ListItemSelector.Application.ListItems.Commands.UpdateListItem;
using Jimx.ListItemSelector.Application.ListItems.Queries.GetListItemById;
using Jimx.ListItemSelector.Application.ListItems.Queries.GetListItems;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Jimx.ListItemSelector.Api.Controllers;

[ApiController]
[Route("api/list-items")]
public class ListItemsController : ControllerBase
{
    private readonly IMediator _mediator;

    public ListItemsController(IMediator mediator)
    {
        _mediator = mediator;
    }

    [HttpPost]
    public async Task<ActionResult<int>> Create([FromBody] CreateListItemCommand request, CancellationToken cancellationToken)
    {
        var result = await _mediator.Send(request, cancellationToken);
        if (!result.IsSuccess)
        {
            return BadRequest(result.Errors);
        }
        
        return Ok(result.Value);
    }
    
    [HttpGet]
    public async Task<ActionResult<List<ListItem>>> GetAll([FromQuery] string? name,
        [FromQuery] string? description,
        CancellationToken cancellationToken)
    {
        var items = await _mediator.Send(new GetListItemsQuery(name, description), cancellationToken);
        return Ok(items);
    }
    
    [HttpGet("{id}")]
    public async Task<ActionResult<ListItem?>> GetById(int id, CancellationToken cancellationToken)
    {
        var item = await _mediator.Send(new GetListItemByIdQuery(id), cancellationToken);
        if (item == null)
        {
            return NotFound();
        }
        
        return Ok(item);
    }
    
    [HttpPut("{id}")]
    public async Task<IActionResult> Update(int id, [FromBody] UpdateListItemCommand request, CancellationToken cancellationToken)
    {
        if (id != request.Id)
        {
            return BadRequest("Mismatched IDs");
        }

        var result = await _mediator.Send(request, cancellationToken);
        if (!result.IsSuccess)
        {
            return BadRequest(result.Errors);
        }
        
        return NoContent();
    }
    
    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(int id, CancellationToken cancellationToken)
    {
        var result = await _mediator.Send(new DeleteListItemCommand(id), cancellationToken);
        if (!result.IsSuccess)
        {
            return BadRequest(result.Errors);
        }
        
        return NoContent();
    }
}