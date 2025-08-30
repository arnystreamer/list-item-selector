using Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;
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
        var id = await _mediator.Send(request, cancellationToken);
        return Ok(id);
    }
}