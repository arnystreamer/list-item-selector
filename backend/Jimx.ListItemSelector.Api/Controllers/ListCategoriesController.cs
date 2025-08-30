using Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;
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
        var id = await _mediator.Send(request, cancellationToken);
        return Ok(id);
    }
}