using Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;
using Jimx.ListItemSelector.Application.ListCategories.Commands.DeleteListCategory;
using Jimx.ListItemSelector.Application.ListCategories.Commands.UpdateListCategory;
using Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategories;
using Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategoryById;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Jimx.ListItemSelector.Api.Controllers;

[ApiController]
[Route("api/list-categories")]
public class ListCategoriesController : ControllerBase
{
    private readonly IMediator _mediator;

    public ListCategoriesController(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    [HttpPost]
    public async Task<ActionResult<int>> Create([FromBody] CreateListCategoryCommand request, CancellationToken cancellationToken)
    {
        var result = await _mediator.Send(request, cancellationToken);
        if (!result.IsSuccess)
        {
            return BadRequest(result.Errors);
        }
        
        return Ok(result.Value);
    }

    [HttpGet]
    public async Task<ActionResult<List<ListCategory>>> GetAll([FromQuery] string? name,
        CancellationToken cancellationToken)
    {
        var items = await _mediator.Send(new GetListCategoriesQuery(name), cancellationToken);
        return Ok(items);
    }
    
    [HttpGet("{id}")]
    public async Task<ActionResult<ListCategory?>> GetById(int id, CancellationToken cancellationToken)
    {
        var item = await _mediator.Send(new GetListCategoryByIdQuery(id), cancellationToken);
        if (item == null)
        {
            return NotFound();
        }
        
        return Ok(item);
    }
    
    [HttpPut("{id}")]
    public async Task<IActionResult> Update(int id, [FromBody] UpdateListCategoryCommand request, CancellationToken cancellationToken)
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
        var result = await _mediator.Send(new DeleteListCategoryCommand(id), cancellationToken);
        if (!result.IsSuccess)
        {
            return BadRequest(result.Errors);
        }
        
        return NoContent();
    }
}